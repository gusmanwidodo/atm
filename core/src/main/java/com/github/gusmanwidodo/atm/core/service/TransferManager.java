package com.github.gusmanwidodo.atm.core.service;

public class TransferManager {
    private double senderBalance;
    private double senderOwedBalance;
    private double beneficiaryBalance;
    private double beneficiaryOwedBalance;
    private double availablePaymentAmount;
    private double pendingPaymentAmount;
    private double beneficiaryToSenderOwedBalance;

    public TransferManager(
            double senderBalance,
            double senderOwedBalance,
            double beneficiaryBalance,
            double beneficiaryOwedBalance,
            double beneficiaryToSenderOwedBalance) {
        this.senderBalance = senderBalance;
        this.senderOwedBalance = senderOwedBalance;
        this.beneficiaryBalance = beneficiaryBalance;
        this.beneficiaryOwedBalance = beneficiaryOwedBalance;
        this.beneficiaryToSenderOwedBalance = beneficiaryToSenderOwedBalance;
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

        if (beneficiaryToSenderOwedBalance > 0) {
            senderOwedBalance -= amount;
            beneficiaryOwedBalance += amount;
            availablePaymentAmount -= beneficiaryToSenderOwedBalance;
        }

        if (availablePaymentAmount < 0) {
            pendingPaymentAmount += availablePaymentAmount;
            availablePaymentAmount = 0;
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
