package com.example.bankaccount.service.savingaccount.impl;

import com.example.bankaccount.domain.SavingAccount;
import com.example.bankaccount.domain.Transaction;
import com.example.bankaccount.domain.TransactionType;
import com.example.bankaccount.repository.SavingAccountRepository;
import com.example.bankaccount.service.savingaccount.SavingAccountService;
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
@Qualifier("savingAccountServiceImpl")
@RequiredArgsConstructor
public class SavingAccountServiceImpl implements SavingAccountService {
    private final SavingAccountRepository savingAccountRepository;
    private final TransactionService transactionService;

    @Override
    public void deposit(String accountNumber, double amount) {
        Optional<SavingAccount> accountOpt = savingAccountRepository.findById(accountNumber);
        if (accountOpt.isPresent()) {
            SavingAccount account = accountOpt.get();
            if (account.getBalance() + amount > account.getDepositLimit()) {
                throw new IllegalArgumentException("Deposit limit exceeded");
            }
            account.deposit(amount);
            savingAccountRepository.save(account);

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
        Optional<SavingAccount> accountOpt = savingAccountRepository.findById(accountNumber);
        if (accountOpt.isPresent()) {
            SavingAccount account = accountOpt.get();
            if (amount > account.getBalance()) {
                throw new IllegalArgumentException("Cannot withdraw more than the current balance");
            }
            account.withdraw(amount);
            savingAccountRepository.save(account);

            transactionService.save(Transaction.builder()
                    .accountNumber(accountNumber)
                    .amount(amount)
                    .date(new Date())
                    .type(TransactionType.WITHDRAWAL)
                    .build());

            return true;
        }
        return false;
    }

    @Override
    public List<Transaction> getStatement(String accountNumber) {
        return transactionService.getStatement(accountNumber);
    }
}
