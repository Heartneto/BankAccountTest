package com.example.bankaccount.service.transaction.impl;

import com.example.bankaccount.domain.Transaction;
import com.example.bankaccount.repository.TransactionRepository;
import com.example.bankaccount.service.transaction.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getStatement(String accountNumber) {
        return transactionRepository.findByAccountNumberOrderByDateDesc(accountNumber);
    }
}
