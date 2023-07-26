package com.openclassrooms.projet6.paymybuddy.servicetest;

import com.openclassrooms.projet6.paymybuddy.dto.BuddyConnectedDto;
import com.openclassrooms.projet6.paymybuddy.dto.TransactionDto;
import com.openclassrooms.projet6.paymybuddy.service.PayMyBuddyService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.openclassrooms.projet6.paymybuddy.constants.Constants.NON_EXISTING_ACCOUNT;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Sql(scripts = "classpath:creation_alimentation_BDD_test.sql")
@Transactional
public class PayMyBuddyServiceTest {
    @Autowired
    PayMyBuddyService payMyBuddyServiceImpl;


    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'getBalanceAccount' de la classe  payMyBuddyServiceImpl
    //*********************************************************************************************************
    @Test
    void testGetBalanceAccountWithExistingConnectionId() {
        // ARRANGE
        int connectionId = 2;

        // ACT
        float balanceAccount = payMyBuddyServiceImpl.getBalanceAccount(connectionId);

        // ASSERT
        assertTrue(balanceAccount == 200);
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

        // ACT
        List<TransactionDto> transactionDtos = payMyBuddyServiceImpl.getTransactions(connectionId);

        // ASSERT
        assertTrue(transactionDtos.size() == 2);

        assertTrue(transactionDtos.get(0).getName().equals("buddy3"));
        assertTrue(transactionDtos.get(0).getDescription().equals("Transaction PMB_account2 vers PMB_account3"));
        assertTrue(transactionDtos.get(0).getAmount()== 30);

        assertTrue(transactionDtos.get(1).getName().equals("buddy4"));
        assertTrue(transactionDtos.get(0).getDescription().equals("Transaction PMB_account2 vers PMB_account4"));
        assertTrue(transactionDtos.get(0).getAmount()== 40);
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

        // ACT
        List<BuddyConnectedDto> buddiesConnectedDtos = payMyBuddyServiceImpl.getBuddiesConnected(connectionId);

        // ASSERT
        assertTrue(buddiesConnectedDtos.size() == 3);
        assertTrue(buddiesConnectedDtos.get(0).getName().equals("buddy3"));
        assertTrue(buddiesConnectedDtos.get(1).getName().equals("buddy4"));
        assertTrue(buddiesConnectedDtos.get(2).getName().equals("buddy5"));
    }

    @Test
    void testGetBuddiesConnectedWithNonExistingConnectionId() {
        // ARRANGE
        int connectionId = 100;

        // ACT, ASSERT
        assertTrue(payMyBuddyServiceImpl.getBuddiesConnected(connectionId).isEmpty());
    }

    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'addConnection' de la classe  payMyBuddyServiceImpl
    //*********************************************************************************************************




    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'addTransaction' de la classe  payMyBuddyServiceImpl
    //*********************************************************************************************************




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
