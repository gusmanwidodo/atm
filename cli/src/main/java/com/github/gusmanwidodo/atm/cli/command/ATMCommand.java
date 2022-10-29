package com.github.gusmanwidodo.atm.cli.command;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class ATMCommand {

    @ShellMethod(key = "login", value = "Logs in as this customer and creates the customer if not exist")
    public void login(@ShellOption String userName) {
        double balance = 0;
        System.out.println("Hello, " + userName + "!");
        printBalance(balance);
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
