package com.openclassrooms.projet6.paymybuddy.repositorytest;


import com.openclassrooms.projet6.paymybuddy.model.PmbAccount;
import com.openclassrooms.projet6.paymybuddy.repository.PmbAccountRepository;
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
public class PmbAccountRepositoryCrudTest {
    private static Logger logger = LoggerFactory.getLogger(PmbAccountRepositoryCrudTest.class);

    @Autowired
    private PmbAccountRepository pmbAccountRepository;

    @BeforeAll
    static void startingCode(){
        logger.debug("*** TESTS UNITAIRES ***  des méthodes CRUD de l'interface PmbAccountRepository");
    }

    //*********************************************************************************************************
    //  Test unitaire de la méthode 'findAll' de l'interface  PmbAccountRepository
    //*********************************************************************************************************
    @Test
    void testFindAllPmbAccounts() {
        // ARRANGE
        float balanceExistingConnection = 200;
        int idExistingConnection = 2;

        // ACT
        List<PmbAccount> pmbAccounts = pmbAccountRepository.findAll();

        // ASSERT
        assertTrue(pmbAccounts.size() == 9);
        assertTrue(pmbAccounts.get(idExistingConnection - 1).getBalance()==balanceExistingConnection);
    }

    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'findById' de l'interface  PmbAccountRepository
    //*********************************************************************************************************
    @Test
    void testFindAnExistingPmbAccount() {
        // ARRANGE, ACT
        float balanceExistingConnection = 200;
        int idExistingConnection = 2;

        // ACT
        PmbAccount pmbAccount = pmbAccountRepository.findById(idExistingConnection).get();

        // ASSERT
        assertNotNull(pmbAccount);
        assertTrue(pmbAccount.getBalance()== balanceExistingConnection);
    }

    @Test
    void testFindANonExistingPmbAccount() {
        // ARRANGE, ACT
        int idNonExistingPmbAccount = 10;
        Optional<PmbAccount> optPmbAccount = pmbAccountRepository.findById(idNonExistingPmbAccount);

        // ASSERT
        assertTrue(optPmbAccount.isEmpty());
    }


    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'save' de l'interface  PmbAccountRepository
    //*********************************************************************************************************

}

