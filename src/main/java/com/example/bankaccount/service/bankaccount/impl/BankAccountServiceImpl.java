package com.example.bankaccount.service.bankaccount.impl;

import com.example.bankaccount.domain.BankAccount;
import com.example.bankaccount.domain.Transaction;
import com.example.bankaccount.domain.TransactionType;
import com.example.bankaccount.repository.BankAccountRepository;
import com.example.bankaccount.service.bankaccount.BankAccountService;
import com.example.bankaccount.service.transaction.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Qualifier("bankAccountServiceImpl")
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final TransactionService transactionService;

    @Override
    public void deposit(String accountNumber, double amount) {
        Optional<BankAccount> accountOpt = bankAccountRepository.findById(accountNumber);
        if (accountOpt.isPresent()) {
            BankAccount account = accountOpt.get();
            account.deposit(amount);
            bankAccountRepository.save(account);

            transactionService.save(Transaction.builder()
                    .accountNumber(accountNumber)
                    .amount(amount)
                    .date(new Date())
                    .type(TransactionType.DEPOSIT)
                    .build());
        }
    }

    @Override
    public boolean withdraw(String accountNumber, double amount) {
        Optional<BankAccount> accountOpt = bankAccountRepository.findById(accountNumber);
        if (accountOpt.isPresent()) {
            BankAccount account = accountOpt.get();
            if (account.withdraw(amount)) {
                bankAccountRepository.save(account);

                transactionService.save(Transaction.builder()
                        .accountNumber(accountNumber)
                        .amount(amount)
                        .date(new Date())
                        .type(TransactionType.WITHDRAWAL)
                        .build());

                return true;
            }
        }
        return false;
    }

    @Override
    public List<Transaction> getStatement(String accountNumber) {
        return transactionService.getStatement(accountNumber);
    }
}
