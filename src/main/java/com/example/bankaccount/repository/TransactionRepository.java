package com.example.bankaccount.repository;

import com.example.bankaccount.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByAccountNumberOrderByDateDesc(String accountNumber);
}
