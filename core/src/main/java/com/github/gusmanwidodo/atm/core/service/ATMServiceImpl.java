package com.github.gusmanwidodo.atm.core.service;

import com.github.gusmanwidodo.atm.core.model.Account;
import com.github.gusmanwidodo.atm.core.model.Customer;
import com.github.gusmanwidodo.atm.core.repository.CustomerRepository;

import java.util.Optional;

public class ATMServiceImpl implements ATMService {
    private final CustomerRepository customerRepository;

    public ATMServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer getCustomer(long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        return customer.get();
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
