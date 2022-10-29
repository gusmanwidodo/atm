package com.github.gusmanwidodo.atm.core.service;

import com.github.gusmanwidodo.atm.core.model.Account;
import com.github.gusmanwidodo.atm.core.model.Customer;

public interface ATMService {
    Customer getCustomer(long customerId);
    Account getAccount(long accountId);
    void login(String userName);
    void logout(long customerId);
    void deposit(long accountId, double amount);
    void transfer(long accountId, double amount, String accountBank, String accountNumber, String accountHolder);
}
