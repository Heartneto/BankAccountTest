package com.example.bankaccount.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class SavingAccount{
    @Id
    private String accountNumber;
    private double balance;
    private double depositLimit;

    public void deposit(double amount) {
        if (this.getBalance() + amount > this.depositLimit) {
            throw new IllegalArgumentException("Deposit limit exceeded");
        }
        this.balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > this.balance) {
            return false;
        }
        this.balance -= amount;
        return true;
    }
}
