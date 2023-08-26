package com.openclassrooms.projet6.paymybuddy.repositoryintegration;


import com.openclassrooms.projet6.paymybuddy.model.Connection;
import com.openclassrooms.projet6.paymybuddy.model.PmbAccount;
import com.openclassrooms.projet6.paymybuddy.repository.ConnectionRepository;
import com.openclassrooms.projet6.paymybuddy.repository.PmbAccountRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "classpath:creation_alimentation_BDD_test.sql")
public class PmbAccountRepositoryIT {
    private static Logger logger = LoggerFactory.getLogger(PmbAccountRepositoryIT.class);

    @Autowired
    private PmbAccountRepository pmbAccountRepository;

    @Autowired
    private ConnectionRepository connectionRepository;


    @BeforeAll
    static void startingCode(){
        logger.debug("*** TESTS D'INTEGRATION ***  des méthodes CRUD de l'interface PmbAccountRepository");
    }

    //*********************************************************************************************************
    //  Test d'intégration de la méthode 'findAll' de l'interface  PmbAccountRepository
    //*********************************************************************************************************
    @Test
    void testFindAllPmbAccounts() {
        // ARRANGE
        float balanceExistingConnection = 200;
        int idExistingConnection = 2;

        // ACT
        List<PmbAccount> pmbAccounts = pmbAccountRepository.findAll();

        // ASSERT
        assertEquals(pmbAccounts.size(), 9);
        assertEquals(pmbAccounts.get(idExistingConnection - 1).getBalance(), balanceExistingConnection);
    }

    //*********************************************************************************************************
    //  Test d'intégration de la méthode 'findById' de l'interface  PmbAccountRepository
    //*********************************************************************************************************
    @Test
    void testFindByIdAnExistingPmbAccount() {
        // ARRANGE, ACT
        int idExistingConnection = 2;
        float balanceExistingConnection = 200;

        // ACT
        PmbAccount pmbAccount = pmbAccountRepository.findById(idExistingConnection).get();

        // ASSERT
        assertNotNull(pmbAccount);
        assertEquals(pmbAccount.getBalance(), balanceExistingConnection);
    }

    @Test
    void testFindByIdANonExistingPmbAccount() {
        // ARRANGE, ACT
        int idNonExistingPmbAccount = 10;

        // ACT, ASSERT
        assertTrue(pmbAccountRepository.findById(idNonExistingPmbAccount).isEmpty());
    }


    //*********************************************************************************************************
    //  Test d'intégration de la méthode 'save' de l'interface  PmbAccountRepository
    //*********************************************************************************************************
    @Test
    @Transactional
    @Commit
    void testSaveNewPmbAccount() {
        // ARRANGE
        PmbAccount newPmbAccount = new PmbAccount();
        int idExistingConnection= 7;
        Connection existingConnection = connectionRepository.findById(idExistingConnection).get();
        newPmbAccount.setConnection(existingConnection);

        // ACT
        PmbAccount pmbAccountSaved  = pmbAccountRepository.save(newPmbAccount);

        // ASSERT
        int pmbAccountSavedId = pmbAccountSaved.getPmbAccountId();
        assertNotNull(pmbAccountSavedId);
        assertTrue(pmbAccountRepository.findById(pmbAccountSavedId).isPresent());
    }


    @Test
    void testSaveAnExistingPmbAccount() {
        // ARRANGE
        int existingId = 2;
        PmbAccount existingPmbAccount = pmbAccountRepository.findById(existingId).get();
        int nbPmbAccountInitial = pmbAccountRepository.findAll().size();

        // ACT
        PmbAccount pmbAccountSaved = pmbAccountRepository.save(existingPmbAccount);

        // ASSERT : on vérifie que l'enregistrement est inchangé (pas d'ajout , ni de modification , ni suppression)
        int pmbAccountSavedId = pmbAccountSaved.getPmbAccountId();
        assertNotNull(pmbAccountSavedId);
        assertTrue(pmbAccountRepository.findById(pmbAccountSavedId).isPresent());
        assertEquals(pmbAccountRepository.findAll().size(), nbPmbAccountInitial);
    }

    //*********************************************************************************************************
    //  Test d'intégration de la méthode 'deleteById' de l'interface  PmbAccountRepository
    //  non applicable car relation OneToOne avec Connection
    //  et supprimer un compte ne doit pas supprimer la connection associée
    //*********************************************************************************************************



    //*********************************************************************************************************
    //  Test d'intégration de l'interface PmbAccountRepository pour la mise à jour
    //*********************************************************************************************************
    @Test
    void testUpdateByIdAnExistingPmbAccountWithANewBalance() {
        // ARRANGE
        int existingId = 2;
        float newBalance = 1000;
        PmbAccount existingPmbAccount = pmbAccountRepository.findById(existingId).get();
        existingPmbAccount.setBalance(newBalance);

        // ACT
        PmbAccount pmbAccountSaved = pmbAccountRepository.save(existingPmbAccount);

        // ASSERT
        assertNotNull(pmbAccountSaved.getPmbAccountId());

        assertEquals(pmbAccountSaved.getBalance(), newBalance);

        assertTrue(pmbAccountRepository.findById(pmbAccountSaved.getPmbAccountId()).isPresent());
    }
}

