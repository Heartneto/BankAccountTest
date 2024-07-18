package com.example.bankaccount.service;

import com.example.bankaccount.domain.SavingAccount;
import com.example.bankaccount.domain.Transaction;
import com.example.bankaccount.repository.SavingAccountRepository;
import com.example.bankaccount.service.savingaccount.impl.SavingAccountServiceImpl;
import com.example.bankaccount.service.transaction.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SavingAccountServiceImplTest {

    @Mock
    private SavingAccountRepository savingAccountRepository;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private SavingAccountServiceImpl savingAccountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeposit() {
        String accountNumber = "123456";
        double amount = 100.0;

        SavingAccount account = new SavingAccount();
        account.setAccountNumber(accountNumber);
        account.setBalance(0.0);
        account.setDepositLimit(500.0);

        when(savingAccountRepository.findById(accountNumber)).thenReturn(Optional.of(account));
        when(savingAccountRepository.save(any(SavingAccount.class))).thenReturn(account);

        savingAccountService.deposit(accountNumber, amount);

        verify(savingAccountRepository, times(1)).findById(accountNumber);
        verify(savingAccountRepository, times(1)).save(any(SavingAccount.class));
        verify(transactionService, times(1)).save(any(Transaction.class));

        assertEquals(100.0, account.getBalance());
    }

    @Test
    void testDepositExceedsLimit() {
        String accountNumber = "123456";
        double amount = 600.0;

        SavingAccount account = new SavingAccount();
        account.setAccountNumber(accountNumber);
        account.setBalance(0.0);
        account.setDepositLimit(500.0);

        when(savingAccountRepository.findById(accountNumber)).thenReturn(Optional.of(account));

        assertThrows(IllegalArgumentException.class, () -> savingAccountService.deposit(accountNumber, amount));

        verify(savingAccountRepository, times(1)).findById(accountNumber);
        verify(savingAccountRepository, never()).save(any(SavingAccount.class));
        verify(transactionService, never()).save(any(Transaction.class));

        assertEquals(0.0, account.getBalance());
    }

    @Test
    void testWithdraw() {
        String accountNumber = "123456";
        double amount = 100.0;

        SavingAccount account = new SavingAccount();
        account.setAccountNumber(accountNumber);
        account.setBalance(200.0);

        when(savingAccountRepository.findById(accountNumber)).thenReturn(Optional.of(account));
        when(savingAccountRepository.save(any(SavingAccount.class))).thenReturn(account);

        boolean result = savingAccountService.withdraw(accountNumber, amount);

        verify(savingAccountRepository, times(1)).findById(accountNumber);
        verify(savingAccountRepository, times(1)).save(any(SavingAccount.class));
        verify(transactionService, times(1)).save(any(Transaction.class));

        assertTrue(result);
        assertEquals(100.0, account.getBalance());
    }

    @Test
    void testWithdrawInsufficientFunds() {
        String accountNumber = "123456";
        double amount = 300.0;

        SavingAccount account = new SavingAccount();
        account.setAccountNumber(accountNumber);
        account.setBalance(200.0);

        when(savingAccountRepository.findById(accountNumber)).thenReturn(Optional.of(account));

        assertThrows(IllegalArgumentException.class, () -> savingAccountService.withdraw(accountNumber, amount));

        verify(savingAccountRepository, times(1)).findById(accountNumber);
        verify(savingAccountRepository, never()).save(any(SavingAccount.class));
        verify(transactionService, never()).save(any(Transaction.class));

        assertEquals(200.0, account.getBalance());
    }

    @Test
    void testGetStatement() {
        savingAccountService.getStatement("123456");
        verify(transactionService, times(1)).getStatement(anyString());
    }
}
