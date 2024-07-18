package com.example.bankaccount.service.savingaccount;

import com.example.bankaccount.domain.Transaction;

import java.util.List;

public interface SavingAccountService {
    /**
     * Déposer un montant dans un compte
     *
     * @param accountNumber numéro de compte
     * @param amount        montant à déposer
     */
    void deposit(String accountNumber, double amount);

    boolean withdraw(String accountNumber, double amount);

    List<Transaction> getStatement(String accountNumber);
}
