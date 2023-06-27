package com.openclassrooms.projet6.paymybuddy.repository;

import com.openclassrooms.projet6.paymybuddy.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
