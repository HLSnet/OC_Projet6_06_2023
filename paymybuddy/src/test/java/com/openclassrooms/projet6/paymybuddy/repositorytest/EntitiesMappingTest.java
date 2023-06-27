package com.openclassrooms.projet6.paymybuddy.repositorytest;



import com.openclassrooms.projet6.paymybuddy.model.Connection;
import com.openclassrooms.projet6.paymybuddy.model.PmbAccount;
import com.openclassrooms.projet6.paymybuddy.repository.ConnectionRepository;
import com.openclassrooms.projet6.paymybuddy.repository.PmbAccountRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;


import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@Transactional
@Sql(scripts = "classpath:creation_alimentation_BDD_test.sql")
public class EntitiesMappingTest {
    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private PmbAccountRepository pmbAccountRepository;

    @Test
    void testMappingBetweenConnectionAndPmbAccount() {
        //**************************************************************************************************************
        //* Test d'accès en lecture de la relation bidirectionnelle OneToOne entre les entités Connection et PmbAccount*
        //**************************************************************************************************************

        // Test de lecture dans la table "connection" grâce la clé etrangère dans la table pmb_account
        int pmbAccountId = 2;
        String email  = "user2_test@gmail.com";

        PmbAccount pmbAccount = pmbAccountRepository.findById(pmbAccountId).get();
        assertTrue(pmbAccount.getConnection().getEmail().equals(email));

        // Test d'accès en lecture dans la table "pmb_account" à partir de la table "connection" grâce à la bidirectionnalité
        int connectionId = 2;
        float balance = 200;

        Connection connection = connectionRepository.findById(connectionId).get();
        assertTrue(balance == connection.getPmbAccount().getBalance());

        //**************************************************************************************************************
        //* Test en création de la relation bidirectionnelle OneToOne entre les entités Connection et PmbAccount       *
        //**************************************************************************************************************


        //**************************************************************************************************************
        //* Test en suppression de la relation bidirectionnelle OneToOne entre les entités Connection et PmbAccount    *
        //**************************************************************************************************************


    }



    @Test
    void testMappingBetweenConnectionBuddiesAndConnection() {
        //**************************************************************************************************************
        //* Test d'accès en lecture de la relation réflexive ManyToMany avec l'entité Connection                       *
        //**************************************************************************************************************

        // Test de lecture de la liste des "buddies connected"
        // ARRANGE
        int connectionId = 2;
        int nbBuddiesConnected = 3;

        // ACT
        Connection connection = connectionRepository.findById(connectionId).get();

        // ASSERT
        assertTrue(connection.getBuddiesConnected().size() == nbBuddiesConnected);
        assertTrue(connection.getBuddiesConnected().get(0).getEmail().equals("user3_test@gmail.com"));
        assertTrue(connection.getBuddiesConnected().get(1).getEmail().equals("user4_test@gmail.com"));
        assertTrue(connection.getBuddiesConnected().get(2).getEmail().equals("user5_test@gmail.com"));


        // Test de lecture de la liste des "buddies connectors"
        // ARRANGE
        int nbBuddiesConnector = 2;

        // ASSERT
        assertTrue(connection.getBuddiesConnector().size() == nbBuddiesConnector);
        assertTrue(connection.getBuddiesConnector().get(0).getEmail().equals("user1_test@gmail.com"));
        assertTrue(connection.getBuddiesConnector().get(1).getEmail().equals("user9_test@gmail.com"));


        //**************************************************************************************************************
        //* Test en création de la relation réflexive ManyToMany avec l'entité Connection                              *
        //**************************************************************************************************************


        //**************************************************************************************************************
        //* Test en suppression de la relation réflexive ManyToMany avec l'entité Connection                           *
        //**************************************************************************************************************

    }


    @Test
    void testMappingBetweenPmbAccountAndTransaction() {
        //******************************************************************************************************************************
        //* Test d'accès en lecture de la relation bidirectionnelle ManyToOne et OneToMany entre les entités PmbAccount et Transaction *
        //******************************************************************************************************************************

        // Test de lecture de la liste des 'receiver' d'une transaction à partir d'un compte pmb_account 'sender'
        // ARRANGE
        int pmbAccountId = 5;
        int nbConnectionReceivers = 2;

        // ACT
        PmbAccount pmbAccount = pmbAccountRepository.findById(pmbAccountId).get();

        // ASSERT
        assertTrue(pmbAccount.getReceiverTransactions().size() == nbConnectionReceivers);
        assertTrue(pmbAccount.getReceiverTransactions().get(0).getDescription().equals("Transaction user5 vers user6"));
        assertTrue(pmbAccount.getReceiverTransactions().get(1).getDescription().equals("Transaction user5 vers user7"));


        // Test de lecture de la liste des 'sender' pour un compte pmb_account 'receiver'
        // ARRANGE
        int nbConnectionSenders = 3;

        // ASSERT
        assertTrue(pmbAccount.getSenderTransactions().size() == nbConnectionSenders);
        assertTrue(pmbAccount.getSenderTransactions().get(0).getPmbAccountSender().getPmbAccountId() == 3);
        assertTrue(pmbAccount.getSenderTransactions().get(1).getPmbAccountSender().getPmbAccountId() == 4);
        assertTrue(pmbAccount.getSenderTransactions().get(2).getPmbAccountSender().getPmbAccountId() == 9);


        //***********************************************************************************************************************
        //* Test en création de la relation bidirectionnelle ManyToOne et OneToMany entre les entités PmbAccount et Transaction *
        //***********************************************************************************************************************


        //**************************************************************************************************************************
        //* Test en suppression de la relation bidirectionnelle ManyToOne et OneToMany entre les entités PmbAccount et Transaction *
        //**************************************************************************************************************************

    }

}