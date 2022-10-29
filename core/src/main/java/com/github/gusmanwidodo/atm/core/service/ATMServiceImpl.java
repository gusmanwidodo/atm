package com.github.gusmanwidodo.atm.core.service;

import com.github.gusmanwidodo.atm.core.constant.Bank;
import com.github.gusmanwidodo.atm.core.constant.Status;
import com.github.gusmanwidodo.atm.core.model.Account;
import com.github.gusmanwidodo.atm.core.model.Customer;
import com.github.gusmanwidodo.atm.core.model.Payment;
import com.github.gusmanwidodo.atm.core.model.Transaction;
import com.github.gusmanwidodo.atm.core.repository.AccountRepository;
import com.github.gusmanwidodo.atm.core.repository.CustomerRepository;
import com.github.gusmanwidodo.atm.core.repository.PaymentRepository;
import com.github.gusmanwidodo.atm.core.repository.TransactionRepository;

import java.time.LocalDate;
import java.util.*;

public class ATMServiceImpl implements ATMService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final PaymentRepository paymentRepository;
    private HashMap<String, Long> authData;

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
    public Account getAccount(long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        return account.get();
    }

    @Override
    public HashMap<String, Long> getAuthData() {
        return authData;
    }

    @Override
    public void login(String userName) {
        Optional<Customer> optCustomer = customerRepository.findByUserName(userName);
        Customer customer = optCustomer.get();

        Optional<Account> optAccount = accountRepository.findByCustomerId(customer.getId());
        Account account = optAccount.get();

        authData.put("customerId", customer.getId());
        authData.put("accountId", account.getId());
    }

    @Override
    public void logout() {
        authData = new HashMap<String, Long>();
    }

    @Override
    public void deposit(long accountId, double amount) {
        Account account = this.getAccount(accountId);
        double newBalanceAmount = account.getBalanceAmount() + amount;

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
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
        Account fromAccount = this.getAccount(accountId);
        Customer fromCustomer = this.getCustomer(fromAccount.getCustomerId());

        Account toAccount = accountRepository.findByNumber(accountNumber).get();
        Customer toCustomer = this.getCustomer(toAccount.getCustomerId());

        // subtract sender balance
        Transaction transaction1 = new Transaction();
        transaction1.setAmount(-amount);
        transaction1.setAccountId(fromAccount.getId());
        transaction1.setTotalBalance(fromAccount.getBalanceAmount());
        transaction1.setCreatedAt(LocalDate.now());
        transactionRepository.save(transaction1);

        fromAccount.setBalanceAmount(fromAccount.getBalanceAmount() - amount);
        accountRepository.save(fromAccount);

        // create payment record
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setAccountId(fromAccount.getId());
        payment.setFromAccountBank(Bank.INTERNAL);
        payment.setFromAccountNumber(fromAccount.getNumber());
        payment.setFromAccountHolder(fromCustomer.getFullName());
        payment.setToAccountBank(Bank.INTERNAL);
        payment.setToAccountNumber(toAccount.getNumber());
        payment.setToAccountHolder(toCustomer.getFullName());
        payment.setFee(0);
        payment.setStatus(Status.PENDING);
        LocalDate now = LocalDate.now();
        payment.setCreatedAt(now);
        payment.setUpdatedAt(now);
        paymentRepository.save(payment);

        // add beneficiary balance
        toAccount.setBalanceAmount(toAccount.getBalanceAmount() + amount);
        Transaction transaction2 = new Transaction();
        transaction2.setAmount(amount);
        transaction2.setAccountId(toAccount.getId());
        transaction2.setTotalBalance(toAccount.getBalanceAmount());
        transaction2.setCreatedAt(LocalDate.now());

        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactionRepository.save(transaction2);

        accountRepository.save(toAccount);
    }
}
