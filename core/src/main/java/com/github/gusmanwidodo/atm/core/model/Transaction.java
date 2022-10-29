package com.github.gusmanwidodo.atm.core.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private long id;
    private long accountId;
    private double amount;
    private double totalBalance;
    private LocalDate createdAt;

    public Transaction() {

    }

    public Transaction(long id, long accountId, double amount, double totalBalance, LocalDate createdAt) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.totalBalance = totalBalance;
        this.createdAt = createdAt;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id && accountId == that.accountId && Double.compare(that.amount, amount) == 0 && Double.compare(that.totalBalance, totalBalance) == 0 && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, amount, totalBalance, createdAt);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", amount=" + amount +
                ", totalBalance=" + totalBalance +
                ", createdAt=" + createdAt +
                '}';
    }
}
