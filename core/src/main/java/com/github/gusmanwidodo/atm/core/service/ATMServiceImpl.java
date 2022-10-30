package com.github.gusmanwidodo.atm.core.service;

import com.github.gusmanwidodo.atm.core.constant.AuthData;
import com.github.gusmanwidodo.atm.core.constant.Bank;
import com.github.gusmanwidodo.atm.core.constant.RefType;
import com.github.gusmanwidodo.atm.core.constant.Status;
import com.github.gusmanwidodo.atm.core.exception.AmountInvalidException;
import com.github.gusmanwidodo.atm.core.model.Account;
import com.github.gusmanwidodo.atm.core.model.Customer;
import com.github.gusmanwidodo.atm.core.model.Payment;
import com.github.gusmanwidodo.atm.core.model.Transaction;
import com.github.gusmanwidodo.atm.core.repository.AccountRepository;
import com.github.gusmanwidodo.atm.core.repository.CustomerRepository;
import com.github.gusmanwidodo.atm.core.repository.PaymentRepository;
import com.github.gusmanwidodo.atm.core.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ATMServiceImpl implements ATMService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final PaymentRepository paymentRepository;
    private HashMap<String, Long> authData;

    @Autowired
    public ATMServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, PaymentRepository paymentRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.paymentRepository = paymentRepository;
        this.authData = new HashMap<String, Long>();
    }

    @Override
    public Customer getCustomer(long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        return customer.get();
    }

    @Override
    public Customer getBeneficiary(String userName) {
        Optional<Customer> customer = customerRepository.findByUserName(userName);
        return customer.get();
    }

    @Override
    public Account getAccount(long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        return account.get();
    }

    @Override
    public Account getBeneficiaryAccount(long customerId) {
        Optional<Account> account = accountRepository.findByCustomerId(customerId);
        return account.get();
    }

    @Override
    public HashMap<String, Long> getAuthData() {
        return authData;
    }

    @Override
    public void login(String userName) {
        Optional<Customer> optCustomer = customerRepository.findByUserName(userName);
        if (optCustomer.isEmpty()) {
            register(userName);
            optCustomer = customerRepository.findByUserName(userName);
        }

        Customer customer = optCustomer.get();

        Optional<Account> optAccount = accountRepository.findByCustomerId(customer.getId());
        Account account = optAccount.get();

        authData.put(AuthData.CUSTOMER_ID, customer.getId());
        authData.put(AuthData.ACCOUNT_ID, account.getId());
    }

    void register(String userName) {
        Customer customer = new Customer();
        customer.setUserName(userName);
        customer.setFirstName(userName);
        customer.setLastName(userName);
        customer.setStatus(Status.ACTIVE);

        LocalDate now = LocalDate.now();
        customer.setCreatedAt(now);
        customer.setUpdatedAt(now);
        customer = customerRepository.save(customer);

        long accountNumber = 999999999 + customer.getId();

        Account account = new Account();
        account.setCustomerId(customer.getId());
        account.setNumber(Long.toString(accountNumber));
        account.setBalanceAmount(0);
        account.setStatus(Status.ACTIVE);

        accountRepository.save(account);
    }

    @Override
    public void logout() {
        authData = new HashMap<String, Long>();
    }

    @Override
    public void deposit(long accountId, double amount) {
        if (amount < 0) {
            throw new AmountInvalidException();
        }

        Account account = this.getAccount(accountId);
        double newBalanceAmount = account.getBalanceAmount() + amount;

        Transaction transaction = new Transaction();
        transaction.setAmount(+amount);
        transaction.setAccountId(accountId);
        transaction.setTotalBalance(newBalanceAmount);
        transaction.setCreatedAt(LocalDate.now());
        transactionRepository.save(transaction);

        account.setBalanceAmount(newBalanceAmount);
        account.setUpdatedAt(LocalDate.now());
        accountRepository.save(account);

        // autodebet
        if (account.getOwedAmount() < 0) {
            List<Payment> payments = paymentRepository.findByPending(account.getNumber());
            payments.stream().filter(p -> p.getAccountId() == account.getId()).forEach(p -> {
                settlePayment(p.getId());
                transfer(p.getAccountId(), p.getAmount(), p.getToAccountBank(), p.getToAccountNumber(), p.getToAccountHolder());
            });
        }
    }

    @Override
    public void withdraw(long accountId, double amount) {
        if (amount < 0) {
            throw new AmountInvalidException();
        }

        Account account = this.getAccount(accountId);
        double newBalanceAmount = account.getBalanceAmount() - amount;

        Transaction transaction = new Transaction();
        transaction.setAmount(-amount);
        transaction.setAccountId(accountId);
        transaction.setTotalBalance(newBalanceAmount);
        transaction.setCreatedAt(LocalDate.now());
        transactionRepository.save(transaction);

        account.setBalanceAmount(newBalanceAmount);
        account.setUpdatedAt(LocalDate.now());
        accountRepository.save(account);
    }

    @Override
    public void transfer(long accountId, double amount, String accountBank, String accountNumber, String accountHolder) {
        if (amount < 0) {
            throw new AmountInvalidException();
        }

        // sender
        Account fromAccount = this.getAccount(accountId);
        Customer fromCustomer = this.getCustomer(fromAccount.getCustomerId());

        // beneficiary
        Account toAccount = accountRepository.findByNumber(accountNumber).get();
        Customer toCustomer = this.getCustomer(toAccount.getCustomerId());

        // manage transfer
        TransferManager transferManager = new TransferManager(
                fromAccount.getBalanceAmount(),
                fromAccount.getOwedAmount(),
                toAccount.getBalanceAmount(),
                toAccount.getOwedAmount()
        );

        transferManager.transfer(amount);

        // available payment
        if (transferManager.getAvailablePaymentAmount() > 0) {
            Payment payment = new Payment();
            payment.setAmount(transferManager.getAvailablePaymentAmount());
            payment.setAccountId(fromAccount.getId());
            payment.setFromAccountBank(Bank.INTERNAL);
            payment.setFromAccountNumber(fromAccount.getNumber());
            payment.setFromAccountHolder(fromCustomer.getUserName());
            payment.setToAccountBank(Bank.INTERNAL);
            payment.setToAccountNumber(toAccount.getNumber());
            payment.setToAccountHolder(toCustomer.getUserName());
            payment.setFee(0);
            payment.setStatus(Status.SUCCESS);
            LocalDate now = LocalDate.now();
            payment.setCreatedAt(now);
            payment.setUpdatedAt(now);
            paymentRepository.save(payment);

            Transaction transaction = new Transaction();
            transaction.setAccountId(fromAccount.getId());
            transaction.setRefId(payment.getId());
            transaction.setRefType(RefType.TRANSFER);
            transaction.setPrevBalance(fromAccount.getBalanceAmount());
            transaction.setAmount(-transferManager.getAvailablePaymentAmount());
            transaction.setTotalBalance(transferManager.getSenderBalance());
            transaction.setCreatedAt(LocalDate.now());
            transactionRepository.save(transaction);

            fromAccount.setBalanceAmount(transferManager.getSenderBalance());
            accountRepository.save(fromAccount);

            Transaction transaction2 = new Transaction();
            transaction2.setAccountId(toAccount.getId());
            transaction2.setRefId(payment.getId());
            transaction2.setRefType(RefType.TRANSFER);
            transaction2.setPrevBalance(toAccount.getBalanceAmount());
            transaction2.setAmount(transferManager.getAvailablePaymentAmount());
            transaction2.setTotalBalance(transferManager.getBeneficiaryBalance());
            transaction2.setCreatedAt(LocalDate.now());
            transactionRepository.save(transaction2);

            toAccount.setBalanceAmount(transferManager.getBeneficiaryBalance());
            accountRepository.save(toAccount);

            System.out.println("Transferred $" + transferManager.getAvailablePaymentAmount() + " to " + toCustomer.getUserName());
        }

        // pending payment
        if (transferManager.getPendingPaymentAmount() > 0) {
            Payment payment2 = new Payment();
            payment2.setAmount(transferManager.getPendingPaymentAmount());
            payment2.setAccountId(fromAccount.getId());
            payment2.setFromAccountBank(Bank.INTERNAL);
            payment2.setFromAccountNumber(fromAccount.getNumber());
            payment2.setFromAccountHolder(fromCustomer.getUserName());
            payment2.setToAccountBank(Bank.INTERNAL);
            payment2.setToAccountNumber(toAccount.getNumber());
            payment2.setToAccountHolder(toCustomer.getUserName());
            payment2.setFee(0);
            payment2.setStatus(Status.PENDING);
            LocalDate now = LocalDate.now();
            payment2.setCreatedAt(now);
            payment2.setUpdatedAt(now);
            paymentRepository.save(payment2);

            fromAccount.setOwedAmount(transferManager.getSenderOwedBalance());
            accountRepository.save(fromAccount);

            toAccount.setOwedAmount(transferManager.getBeneficiaryOwedBalance());
            accountRepository.save(toAccount);
        }
    }

    @Override
    public void settlePayment(long paymentId) {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
        Payment payment = optionalPayment.get();
        payment.setStatus(Status.FAILED);
        paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getPendingPayments(long accountId) {
        Account account = this.getAccount(accountId);
        List<Payment> payments = paymentRepository.findByPending(account.getNumber());
        return payments;
    }
}
