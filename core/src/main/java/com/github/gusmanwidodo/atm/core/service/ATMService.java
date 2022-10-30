package com.github.gusmanwidodo.atm.core.service;

import com.github.gusmanwidodo.atm.core.model.Account;
import com.github.gusmanwidodo.atm.core.model.Customer;
import com.github.gusmanwidodo.atm.core.model.Payment;
import com.github.gusmanwidodo.atm.core.model.Transaction;

import java.util.HashMap;
import java.util.List;

public interface ATMService {
    Customer getCustomer(long customerId);
    Customer getBeneficiary(String userName);
    Account getAccount(long accountId);
    Account getBeneficiaryAccount(long customerId);
    HashMap<String, Long> getAuthData();
    void login(String userName);
    void logout();
    void deposit(long accountId, double amount);
    void withdraw(long accountId, double amount);
    void transfer(long accountId, double amount, String accountBank, String accountNumber, String accountHolder);
    List<Transaction> GetOwedTransactions(long accountId);
}
