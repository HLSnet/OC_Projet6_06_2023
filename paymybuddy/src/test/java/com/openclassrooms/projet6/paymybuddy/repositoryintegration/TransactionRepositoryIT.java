package com.openclassrooms.projet6.paymybuddy.repositoryintegration;


import com.openclassrooms.projet6.paymybuddy.model.PmbAccount;
import com.openclassrooms.projet6.paymybuddy.model.Transaction;
import com.openclassrooms.projet6.paymybuddy.repository.ConnectionRepository;
import com.openclassrooms.projet6.paymybuddy.repository.PmbAccountRepository;
import com.openclassrooms.projet6.paymybuddy.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "classpath:creation_alimentation_BDD_test.sql")
public class TransactionRepositoryIT {
    private static Logger logger = LoggerFactory.getLogger(TransactionRepositoryIT.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private PmbAccountRepository pmbAccountRepository;


    @BeforeAll
    static void startingCode(){
        logger.debug("*** TESTS D'INTEGRATION ***  des méthodes CRUD de l'interface TransactionRepository");
    }


    //*********************************************************************************************************
    //  Test d'intégration de la méthode 'findAll' de l'interface  TransactionRepository
    //*********************************************************************************************************
    @Test
    void testFindAllTransactions() {
        // ARRANGE
        String description = "Transaction PMB_account4 vers PMB_account5";
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
    //  Tests d'intégration de la méthode 'findById' de l'interface  TransactionRepository
    //*********************************************************************************************************
    @Test
    void testFindByIdAnExistingTransaction() {
        // ARRANGE, ACT
        int id = 7;
        String description = "Transaction PMB_account4 vers PMB_account5";
        float amount = 50;

        // ACT
        Optional<Transaction> optTransaction = transactionRepository.findById(id);
        Transaction transaction = optTransaction.get();

        // ASSERT
        assertNotNull(transaction);
        assertTrue(transaction.getAmount()== amount);
        assertTrue(transaction.getDescription().equals(description));
    }

    @Test
    void testFindByIdANonExistingTransaction() {
        // ARRANGE, ACT
        int idNonExistingTransaction = 100;
        Optional<Transaction> optTransaction = transactionRepository.findById(idNonExistingTransaction);

        // ASSERT
        assertTrue(optTransaction.isEmpty());
    }

    //*********************************************************************************************************
    //  Test d'intégration de la méthode 'save' de l'interface  TransactionRepository
    //*********************************************************************************************************
    @Test
    @Transactional
    @Commit
    void testSaveNewTransaction() {
        // ARRANGE
        Transaction newTransaction = new Transaction();
        int idExistingPmbAccountSender = 7;
        int idExistingPmbAccountReceiver = 2;
        float amount = 720;
        String description = "Transaction PMB_account7 vers PMB_account2";

        PmbAccount existingConnectionSender = pmbAccountRepository.findById(idExistingPmbAccountSender).get();
        PmbAccount existingConnectionReceiver = pmbAccountRepository.findById(idExistingPmbAccountReceiver).get();

        newTransaction.setPmbAccountSender(existingConnectionSender);
        newTransaction.setPmbAccountReceiver(existingConnectionReceiver);
        newTransaction.setAmount(amount);
        newTransaction.setDescription(description);

        // ACT
        Transaction transaction  = transactionRepository.save(newTransaction);

        // ASSERT
        assertNotNull(transaction.getTransactionId());

        Optional<Transaction> newTransactionOpt = transactionRepository.findById(transaction.getTransactionId());
        assertTrue(newTransactionOpt.isPresent());
    }

    //*********************************************************************************************************
    //  Test d'intégration de la méthode 'deleteById' de l'interface  TransactionRepository
    //*********************************************************************************************************
    @Test
    void testDeleteByIdExistingTransaction() {
        // ARRANGE
        int existingTransaction = 2;

        // ACT
        transactionRepository.deleteById(existingTransaction);

        // ASSERT
        Optional<Transaction> newTransactionOpt = transactionRepository.findById(existingTransaction);
        assertFalse(transactionRepository.findById(existingTransaction).isPresent());
    }




}
