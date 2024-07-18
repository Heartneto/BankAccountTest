package com.example.bankaccount.service;

import com.example.bankaccount.domain.BankAccount;
import com.example.bankaccount.domain.Transaction;
import com.example.bankaccount.repository.BankAccountRepository;
import com.example.bankaccount.service.bankaccount.impl.BankAccountServiceImpl;
import com.example.bankaccount.service.transaction.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BankAccountServiceImplTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeposit() {
        String accountNumber = "123456";
        double amount = 100.0;

        BankAccount account = new BankAccount();
        account.setAccountNumber(accountNumber);
        account.setBalance(0.0);

        when(bankAccountRepository.findById(accountNumber)).thenReturn(Optional.of(account));
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(account);

        bankAccountService.deposit(accountNumber, amount);

        verify(bankAccountRepository, times(1)).findById(accountNumber);
        verify(bankAccountRepository, times(1)).save(any(BankAccount.class));
        verify(transactionService, times(1)).save(any(Transaction.class));

        assertEquals(100.0, account.getBalance());
    }

    @Test
    void testWithdraw() {
        String accountNumber = "123456";
        double amount = 100.0;

        BankAccount account = new BankAccount();
        account.setAccountNumber(accountNumber);
        account.setBalance(200.0);

        when(bankAccountRepository.findById(accountNumber)).thenReturn(Optional.of(account));
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(account);

        boolean result = bankAccountService.withdraw(accountNumber, amount);

        verify(bankAccountRepository, times(1)).findById(accountNumber);
        verify(bankAccountRepository, times(1)).save(any(BankAccount.class));
        verify(transactionService, times(1)).save(any(Transaction.class));

        assertTrue(result);
        assertEquals(100.0, account.getBalance());
    }

    @Test
    void testWithdrawInsufficientFunds() {
        String accountNumber = "123456";
        double amount = 300.0;

        BankAccount account = new BankAccount();
        account.setAccountNumber(accountNumber);
        account.setBalance(200.0);

        when(bankAccountRepository.findById(accountNumber)).thenReturn(Optional.of(account));

        boolean result = bankAccountService.withdraw(accountNumber, amount);

        verify(bankAccountRepository, times(1)).findById(accountNumber);
        verify(bankAccountRepository, never()).save(any(BankAccount.class));
        verify(transactionService, never()).save(any(Transaction.class));

        assertFalse(result);
        assertEquals(200.0, account.getBalance());
    }

    @Test
    void testGetStatement() {
        bankAccountService.getStatement("123456");
        verify(transactionService, times(1)).getStatement(anyString());
    }
}
