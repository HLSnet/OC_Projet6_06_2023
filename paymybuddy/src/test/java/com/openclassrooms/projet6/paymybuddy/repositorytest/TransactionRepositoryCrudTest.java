package com.openclassrooms.projet6.paymybuddy.repositorytest;


import com.openclassrooms.projet6.paymybuddy.model.Transaction;
import com.openclassrooms.projet6.paymybuddy.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@Sql(scripts = "classpath:creation_alimentation_BDD_test.sql")
public class TransactionRepositoryCrudTest {
    private static Logger logger = LoggerFactory.getLogger(TransactionRepositoryCrudTest.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeAll
    static void startingCode(){
        logger.debug("*** TESTS UNITAIRES ***  des méthodes CRUD de l'interface TransactionRepository");
    }


    //*********************************************************************************************************
    //  Test unitaire de la méthode 'findAll' de l'interface  TransactionRepository
    //*********************************************************************************************************
    @Test
    void testFindAllTransactions() {
        // ARRANGE
        String description = "Transaction user4 vers user5";
        float amount = 50;
        int idTransaction = 7;

        // ACT
        List<Transaction> transactions = transactionRepository.findAll();

        // ASSERT
        assertTrue(transactions.size() == 23);
        assertTrue(transactions.get(idTransaction - 1).getAmount()== amount);
        assertTrue(transactions.get(idTransaction - 1).getDescription().equals(description));
    }

    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'findById' de l'interface  TransactionRepository
    //*********************************************************************************************************
    @Test
    void testFindAnExistingTransaction() {
        // ARRANGE, ACT
        String description = "Transaction user4 vers user5";
        float amount = 50;
        int id = 7;

        // ACT
        Optional<Transaction> optTransaction = transactionRepository.findById(id);
        Transaction transaction = optTransaction.get();

        // ASSERT
        assertNotNull(transaction);
        assertTrue(transaction.getAmount()== amount);
        assertTrue(transaction.getDescription().equals(description));
    }

    @Test
    void testFindANonExistingTransaction() {
        // ARRANGE, ACT
        int idNonExistingTransaction = 100;
        Optional<Transaction> optTransaction = transactionRepository.findById(idNonExistingTransaction);

        // ASSERT
        assertTrue(optTransaction.isEmpty());
    }
}
