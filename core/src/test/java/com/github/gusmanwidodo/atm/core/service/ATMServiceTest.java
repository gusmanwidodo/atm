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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class ATMServiceTest {
    public List<Customer> customers;
    public List<Account> accounts;
    public ATMService atmService;

    @Mock
    public CustomerRepository customerRepository;
    @Mock
    public AccountRepository accountRepository;
    @Mock
    public TransactionRepository transactionRepository;
    @Mock
    public PaymentRepository paymentRepository;


    public ATMServiceTest() {
        LocalDate now = LocalDate.now();

        customers = new ArrayList<Customer>();
        customers.add(new Customer(1, "alice", "Alice", "Perry", "active", now, now));
        customers.add(new Customer(2, "bob", "Bob", "Smith", "active", now, now));

        accounts = new ArrayList<Account>();
        accounts.add(new Account(1, 1, "987654321", 100, "active", now, now));
        accounts.add(new Account(2, 2, "987654322", 120, "active", now, now));
    }


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCustomer() {
        Customer expected = customers.get(0);

        // mockup
        when(customerRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
        atmService = new ATMServiceImpl(customerRepository, accountRepository, transactionRepository, paymentRepository);

        // business logic here
        Customer actual = atmService.getCustomer(expected.getId());


        Assert.assertEquals(actual.equals(expected), true);
    }

    @Test
    public void testGetAccount() {
        Account expected = accounts.get(0);

        // mockup
        when(accountRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
        atmService = new ATMServiceImpl(customerRepository, accountRepository, transactionRepository, paymentRepository);

        // business logic here
        Account actual = atmService.getAccount(expected.getId());


        Assert.assertEquals(actual.equals(expected), true);
    }

    @Test
    public void testLogin() {
        Customer customer = customers.get(0);
        Account account = accounts.get(0);

        // mockup
        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));
        when(accountRepository.findByCustomerId(customer.getId())).thenReturn(Optional.of(account));
        atmService = new ATMServiceImpl(customerRepository, accountRepository, transactionRepository, paymentRepository);

        // business logic here
        atmService.login(customer.getUserName());

        HashMap<String, Long> authData = atmService.getAuthData();

        Assert.assertEquals(authData.get("customerId"), customer.getId());
        Assert.assertEquals(authData.get("accountId"), account.getId());
    }

    @Test
    public void testLogout() {
        Customer customer = customers.get(0);
        Account account = accounts.get(0);

        // mockup
        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));
        when(accountRepository.findByCustomerId(customer.getId())).thenReturn(Optional.of(account));
        atmService = new ATMServiceImpl(customerRepository, accountRepository, transactionRepository, paymentRepository);

        // business logic here
        atmService.login(customer.getUserName());

        HashMap<String, Long> authData = atmService.getAuthData();

        Assert.assertEquals(authData.get("customerId"), customer.getId());
        Assert.assertEquals(authData.get("accountId"), account.getId());

        // flash session
        atmService.logout();

        Assert.assertEquals(atmService.getAuthData().isEmpty(), true);
    }

    @Test
    public void testDeposit() {
        Account account = accounts.get(0);
        double amountToDeposit = 10;
        double newBalance = account.getBalanceAmount() + amountToDeposit;

        Transaction transaction = new Transaction();
        transaction.setAmount(amountToDeposit);
        transaction.setAccountId(account.getId());
        transaction.setTotalBalance(newBalance);
        transaction.setCreatedAt(LocalDate.now());

        // mock
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        when(accountRepository.save(account)).thenReturn(account);
        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        atmService = new ATMServiceImpl(customerRepository, accountRepository, transactionRepository, paymentRepository);

        account = atmService.getAccount(account.getId());
        double amountBeforeDeposit = account.getBalanceAmount();

        atmService.deposit(account.getId(), amountToDeposit);
        account = atmService.getAccount(account.getId());

        Assert.assertEquals(account.getBalanceAmount(), newBalance);
    }

    @Test
    public void testTransfer() {
        Customer customer1 = customers.get(0);
        Customer customer2 = customers.get(1);
        Account account1 = accounts.get(0);
        Account account2 = accounts.get(1);

        double amountToTransfer = 10;
        double newBalanceAccount1 = account1.getBalanceAmount() - amountToTransfer;

        double newBalanceAccount2 = account2.getBalanceAmount() + amountToTransfer;

        Transaction transaction1 = new Transaction();
        transaction1.setAmount(-amountToTransfer);
        transaction1.setAccountId(account1.getId());
        transaction1.setTotalBalance(newBalanceAccount1);
        transaction1.setCreatedAt(LocalDate.now());

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(amountToTransfer);
        transaction2.setAccountId(account2.getId());
        transaction2.setTotalBalance(newBalanceAccount2);
        transaction2.setCreatedAt(LocalDate.now());

        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        List<Account> accounts = new ArrayList<Account>();
        accounts.add(account1);
        accounts.add(account2);

        Payment payment = new Payment();
        payment.setAmount(amountToTransfer);
        payment.setAccountId(account1.getId());
        payment.setFromAccountBank(Bank.INTERNAL);
        payment.setFromAccountNumber(account1.getNumber());
        payment.setFromAccountHolder(customer1.getFullName());
        payment.setToAccountBank(Bank.INTERNAL);
        payment.setToAccountNumber(account2.getNumber());
        payment.setToAccountHolder(customer2.getFullName());
        payment.setFee(0);
        payment.setStatus(Status.SUCCESS);

        LocalDate now = LocalDate.now();
        payment.setCreatedAt(now);
        payment.setUpdatedAt(now);

        when(transactionRepository.saveAll(transactions)).thenReturn(transactions);
        when(accountRepository.saveAll(accounts)).thenReturn(accounts);

        when(paymentRepository.save(payment)).thenReturn(payment);

        when(accountRepository.findById(account1.getId())).thenReturn(Optional.of(account1));
        when(accountRepository.findById(account2.getId())).thenReturn(Optional.of(account2));
        when(accountRepository.findByNumber(account2.getNumber())).thenReturn(Optional.of(account2));
        when(customerRepository.findById(customer1.getId())).thenReturn(Optional.of(customer1));
        when(customerRepository.findById(customer2.getId())).thenReturn(Optional.of(customer2));

        atmService = new ATMServiceImpl(customerRepository, accountRepository, transactionRepository, paymentRepository);

        account1 = atmService.getAccount(account1.getId());

        atmService.transfer(account1.getId(), amountToTransfer, Bank.INTERNAL, account2.getNumber(), customer2.getFullName());

        Assert.assertEquals(account1.getBalanceAmount(), newBalanceAccount1);
        Assert.assertEquals(account2.getBalanceAmount(), newBalanceAccount2);
    }
}
