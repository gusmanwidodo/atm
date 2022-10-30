package com.github.gusmanwidodo.atm.core.service;

public class TransferManager {
    private double senderBalance;
    private double senderOwedBalance;
    private double beneficiaryBalance;
    private double beneficiaryOwedBalance;
    private double availablePaymentAmount;
    private double pendingPaymentAmount;

    public TransferManager(double senderBalance, double senderOwedBalance, double beneficiaryBalance, double beneficiaryOwedBalance) {
        this.senderBalance = senderBalance;
        this.senderOwedBalance = senderOwedBalance;
        this.beneficiaryBalance = beneficiaryBalance;
        this.beneficiaryOwedBalance = beneficiaryOwedBalance;
    }

    public void transfer(double amount) {
        senderBalance -= amount;
        beneficiaryBalance += amount;
        availablePaymentAmount = amount;
        pendingPaymentAmount = 0;

        if (senderBalance < 0) {
            senderOwedBalance += senderBalance;

            beneficiaryBalance += senderBalance;
            beneficiaryOwedBalance -= senderBalance;

            availablePaymentAmount += senderBalance;
            pendingPaymentAmount -= senderBalance;

            senderBalance = 0;
        }
    }

    public double getSenderBalance() {
        return senderBalance;
    }

    public double getSenderOwedBalance() {
        return senderOwedBalance;
    }

    public double getBeneficiaryBalance() {
        return beneficiaryBalance;
    }

    public double getBeneficiaryOwedBalance() {
        return beneficiaryOwedBalance;
    }

    public double getAvailablePaymentAmount() {
        return availablePaymentAmount;
    }

    public double getPendingPaymentAmount() {
        return pendingPaymentAmount;
    }
}
