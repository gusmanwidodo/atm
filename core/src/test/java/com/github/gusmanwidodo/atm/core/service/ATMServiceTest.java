package com.github.gusmanwidodo.atm.core.service;

import com.github.gusmanwidodo.atm.core.model.Account;
import com.github.gusmanwidodo.atm.core.model.Customer;
import com.github.gusmanwidodo.atm.core.repository.AccountRepository;
import com.github.gusmanwidodo.atm.core.repository.CustomerRepository;
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

    public ATMServiceTest() {
        LocalDate now = LocalDate.now();

        customers = new ArrayList<Customer>();
        customers.add(new Customer(1, "alice", "Alice", "Perry", "active", now, now));

        accounts = new ArrayList<Account>();
        accounts.add(new Account(1, 1, "987654321", 100, "active", now, now));
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
        atmService = new ATMServiceImpl(customerRepository, accountRepository);

        // business logic here
        Customer actual = atmService.getCustomer(expected.getId());


        Assert.assertEquals(actual.equals(expected), true);
    }

    @Test
    public void testGetAccount() {
        Account expected = accounts.get(0);

        // mockup
        when(accountRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
        atmService = new ATMServiceImpl(customerRepository, accountRepository);

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
        atmService = new ATMServiceImpl(customerRepository, accountRepository);

        // business logic here
        atmService.login(customer.getUserName());

        HashMap<String, Long> authData = atmService.getAuthData();

        Assert.assertEquals(authData.get("customerId"), customer.getId());
        Assert.assertEquals(authData.get("accountId"), account.getId());
    }

    @Test
    public void testLogout() {
        Assert.assertEquals(0, 0);

    }

    @Test
    public void testDeposit() {
        Assert.assertEquals(0, 0);

    }

    @Test
    public void testTransfer() {
        Assert.assertEquals(0, 0);

    }
}
