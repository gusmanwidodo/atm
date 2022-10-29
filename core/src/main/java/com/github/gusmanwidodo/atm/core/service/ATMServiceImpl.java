package com.github.gusmanwidodo.atm.core.service;

import com.github.gusmanwidodo.atm.core.model.Account;
import com.github.gusmanwidodo.atm.core.model.Customer;
import com.github.gusmanwidodo.atm.core.repository.AccountRepository;
import com.github.gusmanwidodo.atm.core.repository.CustomerRepository;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Optional;

public class ATMServiceImpl implements ATMService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private HashMap<String, Long> authData;

    public ATMServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
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
    public void logout(long customerId) {

    }

    @Override
    public void deposit(long accountId, double amount) {

    }

    @Override
    public void transfer(long accountId, double amount, String accountBank, String accountNumber, String accountHolder) {

    }
}
