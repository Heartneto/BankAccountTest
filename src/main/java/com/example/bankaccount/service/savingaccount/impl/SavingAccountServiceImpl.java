package com.example.bankaccount.service.savingaccount.impl;

import com.example.bankaccount.domain.SavingAccount;
import com.example.bankaccount.repository.BankAccountRepository;
import com.example.bankaccount.repository.SavingAccountRepository;
import com.example.bankaccount.repository.TransactionRepository;
import com.example.bankaccount.service.bankaccount.impl.BankAccountServiceImpl;
import com.example.bankaccount.service.savingaccount.SavingAccountService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class SavingAccountServiceImpl extends BankAccountServiceImpl implements SavingAccountService {
    private final SavingAccountRepository savingAccountRepository;

    public SavingAccountServiceImpl(BankAccountRepository bankAccountRepository, TransactionRepository transactionRepository, SavingAccountRepository savingAccountRepository) {
        super(bankAccountRepository, transactionRepository);
        this.savingAccountRepository = savingAccountRepository;
    }

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

            super.deposit(accountNumber, amount);
        }
    }
}
