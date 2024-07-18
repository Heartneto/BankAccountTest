package com.example.bankaccount.controller.rest;

import com.example.bankaccount.domain.Transaction;
import com.example.bankaccount.service.bankaccount.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class BankAccountController {
    private final BankAccountService bankAccountService;

    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<Void> deposit(@PathVariable String accountNumber, @RequestBody double amount) {
        bankAccountService.deposit(accountNumber, amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<Void> withdraw(@PathVariable String accountNumber, @RequestBody double amount) {
        boolean success = bankAccountService.withdraw(accountNumber, amount);
        if (success) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{accountNumber}/statement")
    public ResponseEntity<List<Transaction>> getStatement(@PathVariable String accountNumber) {
        List<Transaction> statement = bankAccountService.getStatement(accountNumber);
        return ResponseEntity.ok(statement);
    }
}
