package com.example.bankaccount.repository;

import com.example.bankaccount.domain.SavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingAccountRepository extends JpaRepository<SavingAccount, String> {
}
