package com.openclassrooms.projet6.paymybuddy.servicetest;

import com.openclassrooms.projet6.paymybuddy.dto.BuddyConnectedDto;
import com.openclassrooms.projet6.paymybuddy.dto.TransactionDto;
import com.openclassrooms.projet6.paymybuddy.model.Connection;
import com.openclassrooms.projet6.paymybuddy.model.Transaction;
import com.openclassrooms.projet6.paymybuddy.repository.ConnectionRepository;
import com.openclassrooms.projet6.paymybuddy.repository.PmbAccountRepository;
import com.openclassrooms.projet6.paymybuddy.repository.TransactionRepository;
import com.openclassrooms.projet6.paymybuddy.service.PayMyBuddyService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;


import static com.openclassrooms.projet6.paymybuddy.constants.Constants.NON_EXISTING_ACCOUNT;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Sql(scripts = "classpath:creation_alimentation_BDD_test.sql")
@Transactional
public class PayMyBuddyServiceTest {
    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private PmbAccountRepository pmbAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    PayMyBuddyService payMyBuddyServiceImpl;


    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'getBalanceAccount' de la classe  payMyBuddyServiceImpl
    //*********************************************************************************************************
    @Test
    void testGetBalanceAccountWithExistingConnectionId() {
        // ARRANGE
        int connectionId = 2;
        float balanceAccountFromDB = pmbAccountRepository.findByConnectionId(connectionId).get().getBalance();

        // ACT
        float balanceAccountFromService = payMyBuddyServiceImpl.getBalanceAccount(connectionId);

        // ASSERT
        assertTrue(balanceAccountFromService == balanceAccountFromDB);
    }

    @Test
    void testGetBalanceAccountWithNonExistingConnectionId() {
        // ARRANGE
        int connectionId = 100;

        // ACT
        float balanceAccount = payMyBuddyServiceImpl.getBalanceAccount(connectionId);

        // ASSERT
        assertTrue(balanceAccount == NON_EXISTING_ACCOUNT);
    }

    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'getTransactions' de la classe  payMyBuddyServiceImpl
    //*********************************************************************************************************
    @Test
    void testGetTransactionsWithExistingConnectionId() {
        // ARRANGE
        int connectionId = 2;
        List<Transaction> transactions = transactionRepository.findTransactionsByConnectionId(connectionId);
        String name = connectionRepository.findById(connectionId).get().getName();

        // ACT
        List<TransactionDto> transactionDtos = payMyBuddyServiceImpl.getTransactions(connectionId);

        // ASSERT
        assertTrue(transactionDtos.size() == transactions.size());
        assertTrue(transactionDtos.get(0).getName().equals(name));
        assertTrue(transactionDtos.get(0).getDescription().equals(transactions.get(0).getDescription()));
        assertTrue(transactionDtos.get(0).getAmount() == transactions.get(0).getAmount());
    }

    @Test
    void testGetTransactionsWithNonExistingConnectionId() {
        // ARRANGE
        int connectionId = 100;

        // ACT, ASSERT
        assertTrue(payMyBuddyServiceImpl.getTransactions(connectionId).isEmpty());
    }


    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'getBuddiesConnected' de la classe  payMyBuddyServiceImpl
    //*********************************************************************************************************
    @Test
    void testGetBuddiesConnectedWithExistingConnectionId() {
        // ARRANGE
        int connectionId = 2;
        List<Connection> buddiesConnected = connectionRepository.findById(connectionId).get().getBuddiesConnected();

        // ACT
        List<BuddyConnectedDto> buddiesConnectedDtos = payMyBuddyServiceImpl.getBuddiesConnected(connectionId);

        // ASSERT
        assertTrue(buddiesConnectedDtos.size() == buddiesConnected.size());
        assertTrue(buddiesConnectedDtos.get(0).getName().equals(buddiesConnected.get(0).getName()));
        assertTrue(buddiesConnectedDtos.get(1).getName().equals(buddiesConnected.get(1).getName()));
        assertTrue(buddiesConnectedDtos.get(2).getName().equals(buddiesConnected.get(2).getName()));
    }

    @Test
    void testGetBuddiesConnectedWithNonExistingConnectionId() {
        // ARRANGE
        int connectionId = 100;

        // ACT, ASSERT
        assertTrue(payMyBuddyServiceImpl.getBuddiesConnected(connectionId).isEmpty());
    }

    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'addBuddyConnected' de la classe  payMyBuddyServiceImpl
    //*********************************************************************************************************
    @Test
    void testAddBuddyConnectedWithExistingConnectionId() {
        // ARRANGE
        int connectionId = 2;
        int connectionBuddyId = 7;

        // ACT
        Boolean resultat = payMyBuddyServiceImpl.addBuddyConnected(connectionId, connectionBuddyId);

        // ASSERT
        assertTrue(resultat);
        Connection connection = connectionRepository.findById(connectionId).get();
        Connection connectionBuddy = connectionRepository.findById(connectionBuddyId).get();
        assertTrue(connection.getBuddiesConnected().contains(connectionBuddy));
    }

    @Test
    void testAddBuddyConnectedWithNonExistingConnectionId() {
        // ARRANGE
        int connectionId = 100;
        int connectionBuddyId = 7;

        // ACT, ASSERT
        assertFalse(payMyBuddyServiceImpl.addBuddyConnected(connectionId, connectionBuddyId));
    }


    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'addTransaction' de la classe  payMyBuddyServiceImpl
    //*********************************************************************************************************
    @Test
    void testAddTransactionWithExistingConnectionId() {
//        // ARRANGE
//        int connectionId = 2;
//
//        int connectionReceiverId = 7;
//        String name = connectionRepository.findById(connectionId).get().getName();
//        String description = "Transaction PMB_account2 vers PMB_account7";
//        float amount= 70;
//
//
//
//        // ACT
//        Boolean resultat = payMyBuddyServiceImpl.addBuddyConnected(connectionId, connectionBuddyId);
//
//        // ASSERT
//        assertTrue(resultat);
//        Connection connection = connectionRepository.findById(connectionId).get();
//        Connection connectionBuddy = connectionRepository.findById(connectionBuddyId).get();
//        assertTrue(connection.getBuddiesConnected().contains(connectionBuddy));
    }


    //public boolean addTransaction(int connectionId, TransactionDto transactionDto) {



    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'getContact' de la classe  payMyBuddyServiceImpl
    //*********************************************************************************************************



    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'getProfile' de la classe  payMyBuddyServiceImpl
    //*********************************************************************************************************




    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'updateProfile' de la classe  payMyBuddyServiceImpl
    //*********************************************************************************************************





}
