package com.example.bankaccount.domain;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SavingAccount extends BankAccount{
    private double depositLimit;

    @Override
    public void deposit(double amount) {
        if (this.getBalance() + amount > this.depositLimit) {
            return;
        }
        super.deposit(amount);
    }
}
