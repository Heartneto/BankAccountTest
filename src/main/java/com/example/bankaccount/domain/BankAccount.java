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

    /**
     * Déposé un montant
     * @param amount à déposer
     */
    public void deposit(double amount) {
        this.balance += amount;
    }

    /**
     * Retiré un montant.
     * Vérifie si le montant à retirer n'est pas supérieur au solde + limite de la découverte. Dans ce cas,
     * l'action peut être effectuée
     * @param amount à retirer
     * @return TRUE (autorisé et déduction du solde) | FALSE sinon
     */
    public boolean withdraw(double amount) {
        if (amount > this.balance + this.overdraftLimit) {
            return false;
        }
        this.balance -= amount;
        return true;
    }
}
