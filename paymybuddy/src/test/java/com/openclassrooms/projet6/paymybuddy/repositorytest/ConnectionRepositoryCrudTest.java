package com.openclassrooms.projet6.paymybuddy.repositorytest;


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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Sql(scripts = "classpath:creation_alimentation_BDD_test.sql")
public class ConnectionRepositoryCrudTest {
    private static Logger logger = LoggerFactory.getLogger(ConnectionRepositoryCrudTest.class);

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private PmbAccountRepository pmbAccountRepository;

    @BeforeAll
    static void startingCode(){
        logger.debug("*** TESTS UNITAIRES ***  des méthodes CRUD de l'interface ConnectionRepository ***");
    }

    //*********************************************************************************************************
    //  Test unitaire de la méthode 'findAll' de l'interface  ConnectionRepository
    //*********************************************************************************************************
    @Test
    void testFindAllConnections() {
        // ARRANGE
        String email = "user2_test@gmail.com";
        String password = "pwd2";

        // ACT
        List<Connection> connections = connectionRepository.findAll();

        // ASSERT
        assertTrue(connections.size() == 9);
        assertTrue(connections.get(1).getEmail().equals(email));
        assertTrue(connections.get(1).getPassword().equals(password));
    }

    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'findById' de l'interface  ConnectionRepository
    //*********************************************************************************************************
    @Test
    void testFindByIdAnExistingConnection() {
        // ARRANGE, ACT
        String emailExistingConnection = "user2_test@gmail.com";
        String passwordExistingConnection = "pwd2";
        int idExistingConnection = 2;

        // ACT
        Optional<Connection> optConnection = connectionRepository.findById(idExistingConnection);
        Connection connection = optConnection.get();

        // ASSERT
        assertNotNull(connection);
        assertTrue(connection.getEmail().equals(emailExistingConnection));
        assertTrue(connection.getPassword().equals(passwordExistingConnection));
    }

    @Test
    void testFindByIdANonExistingConnection() {
        // ARRANGE, ACT
        int idNonExistingConnection = 10;
        Optional<Connection> optConnection = connectionRepository.findById(idNonExistingConnection);

        // ASSERT
        assertTrue(optConnection.isEmpty());
    }

    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'findByEmail' de l'interface  ConnectionRepository
    //*********************************************************************************************************
    @Test
    void testFindByEmailAnExistingConnection() {
        // ARRANGE, ACT
        String emailExistingConnection = "user2_test@gmail.com";
        String passwordExistingConnection = "pwd2";
        int idExistingConnection = 2;

        // ACT
        Optional<Connection> existingConnection = connectionRepository.findByEmail(emailExistingConnection);
        Connection connection = existingConnection.get();

        // ASSERT
        assertNotNull(connection);
        assertTrue(connection.getUserId() == idExistingConnection);
        assertTrue(connection.getPassword().equals(passwordExistingConnection));
    }
    @Test
    void testFindByEmailANonExistingConnection() {
        // ARRANGE, ACT
        String emailNonExistingConnection = "unknownUser_test@gmail.com";

        Optional<Connection> NonExistingConnection = connectionRepository.findByEmail(emailNonExistingConnection);

        // ASSERT
        assertTrue(NonExistingConnection.isEmpty());
    }

    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'save' de l'interface  ConnectionRepository
    //*********************************************************************************************************
    @Test
    void testSaveNewConnection() {

        // ARRANGE
        String emailNewConnection= "user200_test@gmail.com";
        String passwordNewConnection= "pwd200";
        PmbAccount pmbAccount = new PmbAccount();

        Connection newConnection = new Connection();
        newConnection.setEmail(emailNewConnection);
        newConnection.setPassword(passwordNewConnection);
        newConnection.setPmbAccount(pmbAccount);
        pmbAccount.setConnection(newConnection);

        // ACT
        Connection connection = connectionRepository.save(newConnection);
        //connection.setPmbAccount(pmbAccount);

        // ASSERT
        assertNotNull(connection.getUserId());
        assertTrue(connection.getEmail().equals(emailNewConnection));
        assertTrue(connection.getPassword().equals(passwordNewConnection));

        Optional<Connection> retrievedUser = connectionRepository.findById(connection.getUserId());
        assertTrue(retrievedUser.isPresent());
    }

    @Test
    void testSaveAnExistingConnection() {

        // ARRANGE
        String emailExistingConnection= "user2_test@gmail.com";
        String passwordExistingConnection= "pwd2";
        Connection existingConnection = new Connection();
        existingConnection.setEmail(emailExistingConnection);
        existingConnection.setPassword(passwordExistingConnection);

        // ACT , ASSERT
        assertThrows(DataIntegrityViolationException.class, () -> connectionRepository.save(existingConnection));
    }

    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'deleteById' de l'interface  ConnectionRepository
    //*********************************************************************************************************
    @Test
    void testDeleteByIdExistingConnection() {

    }

    @Test
    void testDeleteByIdANonExistingConnection() {

    }

    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'deleteByEmail' de l'interface  ConnectionRepository
    //*********************************************************************************************************

}
