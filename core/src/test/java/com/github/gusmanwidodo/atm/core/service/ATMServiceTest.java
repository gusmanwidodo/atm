package com.github.gusmanwidodo.atm.core.service;

import com.github.gusmanwidodo.atm.core.model.Account;
import com.github.gusmanwidodo.atm.core.model.Customer;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ATMServiceTest {
    private List<Customer> customers;
    private List<Account> accounts;

    private ATMService atmService;

    public ATMServiceTest() {
        LocalDate now = LocalDate.now();

        customers = new ArrayList<Customer>();
        customers.add(new Customer(1, "alice", "Alice", "Perry", "active", now, now));

        accounts = new ArrayList<Account>();

        atmService = new ATMServiceImpl();
    }

    @Test
    void testGetCustomer() {
        Customer expected = customers.get(0);
        Customer actual = atmService.getCustomer(expected.getId());

        Assert.assertEquals(actual.equals(expected), true);
    }

    @Test
    void testGetAccount() {
        Assert.assertEquals(0, 0);

    }

    @Test
    void testLogin() {
        Assert.assertEquals(0, 0);

    }

    @Test
    void testLogout() {
        Assert.assertEquals(0, 0);

    }

    @Test
    void testDeposit() {
        Assert.assertEquals(0, 0);

    }

    @Test
    void testTransfer() {
        Assert.assertEquals(0, 0);

    }
}
