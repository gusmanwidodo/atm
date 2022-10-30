package com.github.gusmanwidodo.atm.cli.command;

import com.github.gusmanwidodo.atm.core.constant.AuthData;
import com.github.gusmanwidodo.atm.core.constant.Bank;
import com.github.gusmanwidodo.atm.core.model.Account;
import com.github.gusmanwidodo.atm.core.model.Customer;
import com.github.gusmanwidodo.atm.core.model.Payment;
import com.github.gusmanwidodo.atm.core.service.ATMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.HashMap;
import java.util.List;

@ShellComponent
public class ATMCommand {
    private final ATMService atmService;

    @Autowired
    public ATMCommand(ATMService atmService) {
        this.atmService = atmService;
    }

    @ShellMethod(key = "login", value = "login [name] - Logs in as this customer and creates the customer if not exist")
    public void login(@ShellOption String userName) {
        atmService.login(userName);
        HashMap<String, Long> authData = atmService.getAuthData();

        Customer customer = atmService.getCustomer(authData.get(AuthData.CUSTOMER_ID));
        Account account = atmService.getAccount(authData.get(AuthData.ACCOUNT_ID));

        System.out.println("Hello, " + customer.getUserName() + "!");

        printBalanceAndOwed(authData.get(AuthData.ACCOUNT_ID));
    }

    @ShellMethod(key = "deposit", value = "deposit [amount] - Deposits this amount to the logged in customer")
    public void deposit(@ShellOption double amount) {
        HashMap<String, Long> authData = atmService.getAuthData();
        Account account = atmService.getAccount(authData.get(AuthData.ACCOUNT_ID));
        atmService.deposit(account.getId(), amount);

        printBalanceAndOwed(authData.get(AuthData.ACCOUNT_ID));
    }

    @ShellMethod(key = "withdraw", value = "withdraw [amount] - Withdraws this amount from the logged in customer")
    public void withdraw(@ShellOption double amount) {
        HashMap<String, Long> authData = atmService.getAuthData();
        Account account = atmService.getAccount(authData.get(AuthData.ACCOUNT_ID));
        atmService.withdraw(account.getId(), amount);

        printBalanceAndOwed(authData.get(AuthData.ACCOUNT_ID));
    }

    @ShellMethod(key = "transfer", value = "transfer [target] [amount] - Transfers this amount from the logged in customer to the target customer")
    public void transfer(@ShellOption String userName, @ShellOption double amount) {
        // sender
        HashMap<String, Long> authData = atmService.getAuthData();
        Account account = atmService.getAccount(authData.get(AuthData.ACCOUNT_ID));

        // beneficiary
        Customer customerBeneficiary = atmService.getBeneficiary(userName);
        Account accountBeneficiary = atmService.getBeneficiaryAccount(customerBeneficiary.getId());
        
        atmService.transfer(account.getId(), amount, Bank.INTERNAL, accountBeneficiary.getNumber(), customerBeneficiary.getFullName());

        printBalanceAndOwed(authData.get(AuthData.ACCOUNT_ID));
    }

    @ShellMethod(key = "logout", value = "logout - Logs out of the current customer")
    public void logout() {
        HashMap<String, Long> authData = atmService.getAuthData();
        Customer customer = atmService.getCustomer(authData.get(AuthData.CUSTOMER_ID));
        System.out.println("Goodbye, " + customer.getUserName() + "!");
    }

    void printBalanceAndOwed(long accountId) {
        Account account = atmService.getAccount(accountId);

        System.out.println("Your balance is $" + account.getBalanceAmount());

        if (account.getOwedAmount() != 0) {
            List<Payment> payments = atmService.getPendingPayments(account.getId());
            payments
                .forEach(p -> {
                    String to = "to";
                    String holder = p.getToAccountHolder();
                    if (p.getAccountId() != account.getId()) {
                        to = "from";
                        holder = p.getFromAccountHolder();
                    }
                    System.out.println("Owed $" + p.getAmount() + " " + to + " " + holder);
                });
        }
    }
}
