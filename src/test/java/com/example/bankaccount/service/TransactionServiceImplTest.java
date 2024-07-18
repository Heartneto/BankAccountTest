package com.example.bankaccount.service;

import com.example.bankaccount.domain.Transaction;
import com.example.bankaccount.domain.TransactionType;
import com.example.bankaccount.repository.TransactionRepository;
import com.example.bankaccount.service.transaction.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {
    @Mock
    TransactionRepository transactionRepository;

    @InjectMocks
    TransactionServiceImpl transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        transactionService.save(Transaction.builder()
                .accountNumber("123456")
                .amount(123456)
                .date(new Date())
                .type(TransactionType.WITHDRAWAL)
                .build());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testGetStatement() {
        Transaction e = Transaction.builder().accountNumber("123456").build();

        when(transactionRepository.findByAccountNumberOrderByDateDesc(anyString())).thenReturn(Collections.singletonList(e));

        assertThat(transactionService.getStatement("123456")).containsExactlyInAnyOrder(e);

        verify(transactionRepository, times(1)).findByAccountNumberOrderByDateDesc(anyString());
    }
}
