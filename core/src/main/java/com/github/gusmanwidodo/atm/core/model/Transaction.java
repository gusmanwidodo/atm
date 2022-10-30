package com.github.gusmanwidodo.atm.core.model;

import com.github.gusmanwidodo.atm.core.constant.RefType;
import org.hibernate.annotations.Any;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private long id;
    private long accountId;
    private long refId;
    private String refType;
    private double amount;
    private double prevBalance;
    private double totalBalance;
    private LocalDate createdAt;

    @OneToOne
    @JoinColumn(name = "refId")
    private Payment payment;

    public Transaction() {

    }

    public Transaction(long id, long accountId, long refId, String refType, double amount, double prevBalance, double totalBalance, LocalDate createdAt, Payment payment) {
        this.id = id;
        this.accountId = accountId;
        this.refId = refId;
        this.refType = refType;
        this.amount = amount;
        this.prevBalance = prevBalance;
        this.totalBalance = totalBalance;
        this.createdAt = createdAt;
        this.payment = payment;
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

    public long getRefId() {
        return refId;
    }

    public void setRefId(long refId) {
        this.refId = refId;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrevBalance() {
        return prevBalance;
    }

    public void setPrevBalance(double prevBalance) {
        this.prevBalance = prevBalance;
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

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id && accountId == that.accountId && refId == that.refId && Double.compare(that.amount, amount) == 0 && Double.compare(that.prevBalance, prevBalance) == 0 && Double.compare(that.totalBalance, totalBalance) == 0 && Objects.equals(refType, that.refType) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, refId, refType, amount, prevBalance, totalBalance, createdAt);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", refId=" + refId +
                ", refType='" + refType + '\'' +
                ", amount=" + amount +
                ", prevBalance=" + prevBalance +
                ", totalBalance=" + totalBalance +
                ", createdAt=" + createdAt +
                '}';
    }
}
