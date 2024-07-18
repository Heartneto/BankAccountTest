package com.example.bankaccount.service.savingaccount;

public interface SavingAccountService {
    /**
     * Déposer un montant dans un compte
     *
     * @param accountNumber numéro de compte
     * @param amount        montant à déposer
     */
    void deposit(String accountNumber, double amount);
}
