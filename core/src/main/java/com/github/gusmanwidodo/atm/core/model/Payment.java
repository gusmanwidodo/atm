package com.github.gusmanwidodo.atm.core.model;

import java.time.LocalDate;
import java.util.Objects;

public class Payment {
    private long id;
    private long accountId;
    private String fromAccountBank;
    private String fromAccountNumber;
    private String fromAccountHolder;
    private String toAccountBank;
    private String toAccountNumber;
    private String toAccountHolder;
    private double amount;
    private double fee;
    private String status;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public Payment() {

    }

    public Payment(long id, long accountId, String fromAccountBank, String fromAccountNumber, String fromAccountHolder, String toAccountBank, String toAccountNumber, String toAccountHolder, double amount, double fee, String status, LocalDate createdAt, LocalDate updatedAt) {
        this.id = id;
        this.accountId = accountId;
        this.fromAccountBank = fromAccountBank;
        this.fromAccountNumber = fromAccountNumber;
        this.fromAccountHolder = fromAccountHolder;
        this.toAccountBank = toAccountBank;
        this.toAccountNumber = toAccountNumber;
        this.toAccountHolder = toAccountHolder;
        this.amount = amount;
        this.fee = fee;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getFromAccountBank() {
        return fromAccountBank;
    }

    public void setFromAccountBank(String fromAccountBank) {
        this.fromAccountBank = fromAccountBank;
    }

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public String getFromAccountHolder() {
        return fromAccountHolder;
    }

    public void setFromAccountHolder(String fromAccountHolder) {
        this.fromAccountHolder = fromAccountHolder;
    }

    public String getToAccountBank() {
        return toAccountBank;
    }

    public void setToAccountBank(String toAccountBank) {
        this.toAccountBank = toAccountBank;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public String getToAccountHolder() {
        return toAccountHolder;
    }

    public void setToAccountHolder(String toAccountHolder) {
        this.toAccountHolder = toAccountHolder;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id == payment.id && accountId == payment.accountId && Double.compare(payment.amount, amount) == 0 && Double.compare(payment.fee, fee) == 0 && Objects.equals(fromAccountBank, payment.fromAccountBank) && Objects.equals(fromAccountNumber, payment.fromAccountNumber) && Objects.equals(fromAccountHolder, payment.fromAccountHolder) && Objects.equals(toAccountBank, payment.toAccountBank) && Objects.equals(toAccountNumber, payment.toAccountNumber) && Objects.equals(toAccountHolder, payment.toAccountHolder) && Objects.equals(status, payment.status) && Objects.equals(createdAt, payment.createdAt) && Objects.equals(updatedAt, payment.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, fromAccountBank, fromAccountNumber, fromAccountHolder, toAccountBank, toAccountNumber, toAccountHolder, amount, fee, status, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", fromAccountBank='" + fromAccountBank + '\'' +
                ", fromAccountNumber='" + fromAccountNumber + '\'' +
                ", fromAccountHolder='" + fromAccountHolder + '\'' +
                ", toAccountBank='" + toAccountBank + '\'' +
                ", toAccountNumber='" + toAccountNumber + '\'' +
                ", toAccountHolder='" + toAccountHolder + '\'' +
                ", amount=" + amount +
                ", fee=" + fee +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
