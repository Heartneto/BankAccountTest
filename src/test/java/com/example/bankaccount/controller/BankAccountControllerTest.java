package com.example.bankaccount.controller;

import com.example.bankaccount.controller.rest.BankAccountController;
import com.example.bankaccount.domain.Transaction;
import com.example.bankaccount.domain.TransactionType;
import com.example.bankaccount.service.bankaccount.BankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BankAccountController.class)
class BankAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankAccountService bankAccountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeposit() throws Exception {
        String accountNumber = "123456";
        double amount = 100.0;

        doNothing().when(bankAccountService).deposit(anyString(), anyDouble());

        mockMvc.perform(post("/api/accounts/{accountNumber}/deposit", accountNumber)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(amount)))
                .andExpect(status().isOk());

        verify(bankAccountService, times(1)).deposit(anyString(), anyDouble());
    }

    @Test
    void testWithdraw() throws Exception {
        String accountNumber = "123456";
        double amount = 100.0;

        when(bankAccountService.withdraw(anyString(), anyDouble())).thenReturn(true);

        mockMvc.perform(post("/api/accounts/{accountNumber}/withdraw", accountNumber)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(amount)))
                .andExpect(status().isOk());

        verify(bankAccountService, times(1)).withdraw(anyString(), anyDouble());
    }

    @Test
    void testWithdrawInsufficientFunds() throws Exception {
        String accountNumber = "123456";
        double amount = 300.0;

        when(bankAccountService.withdraw(anyString(), anyDouble())).thenReturn(false);

        mockMvc.perform(post("/api/accounts/{accountNumber}/withdraw", accountNumber)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(amount)))
                .andExpect(status().isBadRequest());

        verify(bankAccountService, times(1)).withdraw(anyString(), anyDouble());
    }

    @Test
    void testGetStatement() throws Exception {
        String accountNumber = "123456";
        Transaction transaction = Transaction.builder()
                .accountNumber(accountNumber)
                .amount(100.0)
                .date(new Date())
                .type(TransactionType.DEPOSIT)
                .build();

        when(bankAccountService.getStatement(anyString())).thenReturn(Collections.singletonList(transaction));

        mockMvc.perform(get("/api/accounts/{accountNumber}/statement", accountNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountNumber").value(accountNumber))
                .andExpect(jsonPath("$[0].amount").value(100.0))
                .andExpect(jsonPath("$[0].type").value(TransactionType.DEPOSIT.name()));

        verify(bankAccountService, times(1)).getStatement(anyString());
    }
}
