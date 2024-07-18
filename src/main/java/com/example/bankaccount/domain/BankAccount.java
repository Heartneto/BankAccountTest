package com.example.bankaccount.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class BankAccount {
    @Id
    private String accountNumber;
    private double balance;
    private double overdraftLimit;

    public BankAccount() {
        this.accountNumber = UUID.randomUUID().toString();
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > this.balance + this.overdraftLimit) {
            return false;
        }
        this.balance -= amount;
        return true;
    }
}
