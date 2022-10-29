package com.github.gusmanwidodo.atm.cli.command;

import com.github.gusmanwidodo.atm.core.constant.AuthData;
import com.github.gusmanwidodo.atm.core.model.Account;
import com.github.gusmanwidodo.atm.core.model.Customer;
import com.github.gusmanwidodo.atm.core.service.ATMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.HashMap;

@ShellComponent
public class ATMCommand {
    private final ATMService atmService;

    @Autowired
    public ATMCommand(ATMService atmService) {
        this.atmService = atmService;
    }

    @ShellMethod(key = "login", value = "Logs in as this customer and creates the customer if not exist")
    public void login(@ShellOption String userName) {
        atmService.login(userName);
        HashMap<String, Long> authData = atmService.getAuthData();

        Customer customer = atmService.getCustomer(authData.get(AuthData.CUSTOMER_ID));
        Account account = atmService.getAccount(authData.get(AuthData.ACCOUNT_ID));

        System.out.println("Hello, " + customer.getUserName() + "!");
        printBalance(account.getBalanceAmount());
    }

    @ShellMethod(key = "deposit", value = "Deposits this amount to the logged in customer")
    public void deposit(@ShellOption double amount) {
        double balance = 0 + amount;
        printBalance(balance);
    }

    @ShellMethod(key = "transfer", value = "Deposits this amount to the logged in customer")
    public void transfer(@ShellOption String userName, @ShellOption double amount) {
        double balance = 0 + amount;
        System.out.println("Transferred $" + amount + " to " + userName);
        printBalance(balance);

        double owedAmount = 20;
        System.out.println("Owed $" + owedAmount + " to " + userName);
    }

    @ShellMethod(key = "logout", value = "Logs out of the current customer")
    public void logout() {
        String userName = "Alice";
        System.out.println("Goodbye, " + userName + "!");
    }

    void printBalance(double balance) {
        System.out.println("Your balance is $" + balance);
    }
}
