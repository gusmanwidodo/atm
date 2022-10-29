package com.github.gusmanwidodo.atm.core.service;

import com.github.gusmanwidodo.atm.core.model.Account;
import com.github.gusmanwidodo.atm.core.model.Customer;

import java.util.HashMap;

public interface ATMService {
    Customer getCustomer(long customerId);
    Account getAccount(long accountId);
    HashMap<String, Long> getAuthData();
    void login(String userName);
    void logout(long customerId);
    void deposit(long accountId, double amount);
    void transfer(long accountId, double amount, String accountBank, String accountNumber, String accountHolder);
}
