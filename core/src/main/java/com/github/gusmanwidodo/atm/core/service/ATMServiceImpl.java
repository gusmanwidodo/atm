package com.github.gusmanwidodo.atm.core.service;

import com.github.gusmanwidodo.atm.core.model.Account;
import com.github.gusmanwidodo.atm.core.model.Customer;

public class ATMServiceImpl implements ATMService {
    @Override
    public Customer getCustomer(long customerId) {
        return null;
    }

    @Override
    public Account getAccount(long accountId) {
        return null;
    }

    @Override
    public void login(String userName) {

    }

    @Override
    public void logout(long customerId) {

    }

    @Override
    public void deposit(long accountId, double amount) {

    }

    @Override
    public void transfer(long accountId, double amount, String accountBank, String accountNumber, String accountHolder) {

    }
}
