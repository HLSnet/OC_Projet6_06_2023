package com.openclassrooms.projet6.paymybuddy.repositoryintegration;


import com.openclassrooms.projet6.paymybuddy.model.Connection;
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
@Sql(scripts = "classpath:creation_alimentation_BDD_test.sql")  // La base est réinitialisée avant chaque test
public class ConnectionRepositoryIT {
    private static Logger logger = LoggerFactory.getLogger(ConnectionRepositoryIT.class);

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private PmbAccountRepository pmbAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;



    @BeforeAll
    static void startingCode(){
        logger.debug("*** TESTS D'INTEGRATION ***  des méthodes CRUD de l'interface ConnectionRepository ***");
    }

    //*********************************************************************************************************
    //  Tests d'intégration de la méthode 'findAll' de l'interface  ConnectionRepository
    //*********************************************************************************************************
    @Test
    void testFindAllConnections() {
        // ARRANGE
        String email = "connection2_test@gmail.com";
        String password = "pwd2";

        // ACT
        List<Connection> connections = connectionRepository.findAll();

        // ASSERT
        assertTrue(connections.size() == 9);
        assertTrue(connections.get(1).getEmail().equals(email));
        assertTrue(connections.get(1).getPassword().equals(password));
    }

    //*********************************************************************************************************
    //  Tests d'intégration de la méthode 'findById' de l'interface  ConnectionRepository
    //*********************************************************************************************************
    @Test
    @Transactional
    void testFindByIdAnExistingConnection() {
        // ARRANGE, ACT
        String emailExistingConnection = "connection2_test@gmail.com";
        String passwordExistingConnection = "pwd2";
        int idExistingConnection = 2;

        // ACT
        Optional<Connection> optConnection = connectionRepository.findById(idExistingConnection);
        Connection connection = optConnection.get();

        // ASSERT
        assertNotNull(connection);
        assertTrue(connection.getEmail().equals(emailExistingConnection));
        assertTrue(connection.getPassword().equals(passwordExistingConnection));

        List<Connection> buddiesConnected = connection.getBuddiesConnected();
        assertTrue(buddiesConnected.size() == 3);
        assertTrue(buddiesConnected.get(0).getEmail().equals("connection3_test@gmail.com"));
        assertTrue(buddiesConnected.get(1).getEmail().equals("connection4_test@gmail.com"));
        assertTrue(buddiesConnected.get(2).getEmail().equals("connection5_test@gmail.com"));

        List<Connection> buddiesConnector = connection.getBuddiesConnector();
        assertTrue(buddiesConnector.size() == 2);
        assertTrue(buddiesConnector.get(0).getEmail().equals("connection1_test@gmail.com"));
        assertTrue(buddiesConnector.get(1).getEmail().equals("connection9_test@gmail.com"));
    }

    @Test
    void testFindByIdANonExistingConnection() {
        // ARRANGE, ACT
        int idNonExistingConnection = 100;
        Optional<Connection> optConnection = connectionRepository.findById(idNonExistingConnection);

        // ASSERT
        assertTrue(optConnection.isEmpty());
    }

    //*********************************************************************************************************
    //  Tests d'intégration de la méthode 'findByEmail' de l'interface  ConnectionRepository
    //*********************************************************************************************************
    @Test
    void testFindByEmailAnExistingConnection() {
        // ARRANGE, ACT
        String emailExistingConnection = "connection2_test@gmail.com";
        String passwordExistingConnection = "pwd2";
        int idExistingConnection = 2;

        // ACT
        Optional<Connection> existingConnection = connectionRepository.findByEmail(emailExistingConnection);
        Connection connection = existingConnection.get();

        // ASSERT
        assertNotNull(connection);
        assertTrue(connection.getConnectionId() == idExistingConnection);
        assertTrue(connection.getPassword().equals(passwordExistingConnection));
    }
    @Test
    void testFindByEmailANonExistingConnection() {
        // ARRANGE, ACT
        String emailNonExistingConnection = "unknownconnection_test@gmail.com";

        Optional<Connection> NonExistingConnection = connectionRepository.findByEmail(emailNonExistingConnection);

        // ASSERT
        assertTrue(NonExistingConnection.isEmpty());
    }

    //*********************************************************************************************************
    //  Tests d'intégration de la méthode 'save' de l'interface  ConnectionRepository
    //*********************************************************************************************************
    @Test
    @Transactional
    @Commit
    void testSaveNewConnection() {
        // ARRANGE
        Connection newConnection = new Connection();
        String email= "connection10_test@gmail.com";
        String password= "pwd10";
        String name= "buddy10";
        newConnection.setEmail(email);
        newConnection.setPassword(password);
        newConnection.setName(name);
        PmbAccount pmbAccount = new PmbAccount();
        pmbAccount.saveConnection(newConnection);

        // ACT
        Connection connectionSaved = connectionRepository.save(newConnection);

        // ASSERT
        assertNotNull(connectionSaved.getConnectionId());
        assertTrue(connectionSaved.getEmail().equals(email));
        assertTrue(connectionSaved.getPassword().equals(password));

        assertTrue(connectionRepository.findById(connectionSaved.getConnectionId()).isPresent());

        // On vérifie la création dans la table PMB_account d'un enregistrement
        assertTrue(pmbAccountRepository.findByConnectionId(connectionSaved.getConnectionId()).isPresent());
    }

    @Test
    void testSaveAnExistingConnection() {
        // ARRANGE
        int existingId = 2;
        String password= "pwd2";
        Connection existingConnection = connectionRepository.findById(existingId).get();
        existingConnection.setPassword(password);

        // ACT
        Connection connection = connectionRepository.save(existingConnection);

        // ASSERT : on vérifie que l'enregistrement est inchangé (pas d'ajout , ni de modification , ni suppression)
        assertNotNull(connection.getConnectionId());
        assertTrue(connection.getEmail().equals(existingConnection.getEmail()));
        assertTrue(connection.getPassword().equals(existingConnection.getPassword()));

        Optional<Connection> newConnectionOpt = connectionRepository.findById(connection.getConnectionId());
        assertTrue(newConnectionOpt.isPresent());
    }

    //*********************************************************************************************************
    //  Test d'intégration de la méthode 'deleteById' de l'interface  ConnectionRepository
    //*********************************************************************************************************
    @Test
    @Transactional
    @Commit
    void testDeleteByIdExistingConnection() {
        // ARRANGE
        int idExistingConnection = 2;
        Connection existingConnection = connectionRepository.findById(idExistingConnection).get();

        // ACT
        existingConnection.removeBuddiesConnector();
        connectionRepository.deleteById(idExistingConnection);

        // ASSERT
        // On vérifie que l'enregistrement correspondant à l'id fourni dans la table connection est supprimé
        Optional<Connection> connection = connectionRepository.findById(idExistingConnection);
        assertFalse(connection.isPresent());

        // On vérifie que idExistingConnection est supprimé de listes buddiesConnected et buddiesConnector
        assertFalse(existingConnection.getBuddiesConnected().contains(existingConnection));
        assertFalse(existingConnection.getBuddiesConnector().contains(existingConnection));

        // On vérifie que l'enregistrement correspondant dans la table PMB_account est supprimé grace à l'option orphanRemoval = true dans l'entité Connection
        Optional<PmbAccount> pmbAccount = pmbAccountRepository.findByConnectionId(idExistingConnection);
        assertFalse(pmbAccount.isPresent());

        // On vérifie que les enregistrements correspondants au sender dans la table transaction sont supprimés grace à l'option orphanRemoval = true dans l'entité PmbAccount
        List<Transaction> transactions = transactionRepository.findTransactionSendersByConnectionId(idExistingConnection);
        assertTrue(transactions.isEmpty());

        // On vérifie que les enregistrements correspondants au receiver dans la table transaction sont supprimés grace à l'option orphanRemoval = true dans l'entité PmbAccount
        transactions = transactionRepository.findTransactionReceiversByConnectionId(idExistingConnection);
        assertTrue(transactions.isEmpty());
    }


    @Test
    // Rem : si l'identifiant n'existe pas dans la table connection alors il n'y a pas de suppression ni de levée d'exception
    void testDeleteByIdNonExistingConnection() {
        // ARRANGE
        int idExistingConnection = 20;
        int nbConnection = connectionRepository.findAll().size();

        // ACT
        connectionRepository.deleteById(idExistingConnection);

        // ASSERT
        // On vérifie que le nombre de connection dans la table connection est inchangé
        assertTrue(connectionRepository.findAll().size() == nbConnection);
    }


    //*********************************************************************************************************
    //  Test d'intégration de l'interface ConnectionRepository pour la mise à jour
    //*********************************************************************************************************
    @Test
    void testUpdateByIdAnExistingConnectionWithANewPassword() {
        // ARRANGE
        int existingId = 2;
        String password= "pwd2000";
        Connection existingConnection = connectionRepository.findById(existingId).get();
        String email= existingConnection.getEmail();
        existingConnection.setPassword(password);

        // ACT
        Connection connectionUpdated = connectionRepository.save(existingConnection);

        // ASSERT
        assertNotNull(connectionUpdated.getConnectionId());
        assertTrue(connectionUpdated.getEmail().equals(email));
        assertTrue(connectionUpdated.getPassword().equals(password));

        assertTrue(connectionRepository.findById(connectionUpdated.getConnectionId()).isPresent());
    }

    //*********************************************************************************************************
    //  Test ajout buddyConnected dans buddiesConnected
    //*********************************************************************************************************
    @Test
    @Transactional
    @Commit
    void testAddBuddyConnected() {
        // ARRANGE
        int buddyConnectorId = 2;
        int newBuddyConnectedId = 7;

        Connection buddyConnector = connectionRepository.findById(buddyConnectorId).get();
        List<Connection> buddiesConnected = buddyConnector.getBuddiesConnected();
        Connection newBuddyConnected = connectionRepository.findById(newBuddyConnectedId).get();

        // ACT
        buddyConnector.addBuddyConnected(newBuddyConnected);

        // ASSERT
        buddyConnector = connectionRepository.findById(buddyConnectorId).get();
        assertNotNull(buddyConnector);
        buddiesConnected = buddyConnector.getBuddiesConnected();
        assertTrue(buddiesConnected.size() == 4);
        assertTrue(buddiesConnected.get(0).getEmail().equals("connection3_test@gmail.com"));
        assertTrue(buddiesConnected.get(1).getEmail().equals("connection4_test@gmail.com"));
        assertTrue(buddiesConnected.get(2).getEmail().equals("connection5_test@gmail.com"));
        assertTrue(buddiesConnected.get(3).getEmail().equals("connection7_test@gmail.com"));

        newBuddyConnected = connectionRepository.findById(newBuddyConnectedId).get();
        assertNotNull(newBuddyConnected);
        assertTrue(newBuddyConnected.getBuddiesConnector().size() == 5);
        assertTrue(newBuddyConnected.getBuddiesConnector().get(4).getEmail().equals("connection2_test@gmail.com"));
    }
}
