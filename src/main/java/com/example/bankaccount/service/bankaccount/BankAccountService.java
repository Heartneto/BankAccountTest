package com.example.bankaccount.service.bankaccount;

import com.example.bankaccount.domain.Transaction;

import java.util.List;

public interface BankAccountService {
    /**
     * Déposer un montant dans un compte
     *
     * @param accountNumber numéro de compte
     * @param amount        montant à déposer
     */
    void deposit(String accountNumber, double amount);

    /**
     * Retiré un montant dans un compte
     *
     * @param accountNumber numéro de compte
     * @param amount        montant à retirer
     * @return TRUE (action réussie) | FALSE (sinon)
     */
    boolean withdraw(String accountNumber, double amount);

    /**
     * Lister la transaction effectuée par un compte
     *
     * @param accountNumber numéro de compte
     * @return Liste {@link Transaction}
     */
    List<Transaction> getStatement(String accountNumber);
}
