package com.example.bankaccount.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Builder
public class Transaction {
    @Id
    private String id;
    private String accountNumber;
    private double amount;
    private Date date;
    private TransactionType type;

    public Transaction() {
        this.id = UUID.randomUUID().toString();
    }
}
