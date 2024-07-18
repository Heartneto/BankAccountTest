package com.example.bankaccount.service.transaction;

import com.example.bankaccount.domain.Transaction;

import java.util.List;

public interface TransactionService {
    /**
     * Enregistré la transaction
     *
     * @param transaction {@link Transaction}
     */
    void save(Transaction transaction);

    /**
     * Lister les transactions effectuées
     *
     * @param accountNumber numéro de compte
     * @return Liste des transactions
     */
    List<Transaction> getStatement(String accountNumber);
}
