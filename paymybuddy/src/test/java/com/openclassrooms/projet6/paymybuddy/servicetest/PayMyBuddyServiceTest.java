package com.openclassrooms.projet6.paymybuddy.servicetest;

import com.openclassrooms.projet6.paymybuddy.dto.HomeDto;
import com.openclassrooms.projet6.paymybuddy.dto.BuddyConnectedDto;
import com.openclassrooms.projet6.paymybuddy.dto.ProfileDto;
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
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;



import static org.junit.jupiter.api.Assertions.*;

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
    @Commit
    void testGetBalanceAccountWithExistingConnectionId() {
        // ARRANGE
        int connectionId = 2;
        float balanceAccountFromDB = pmbAccountRepository.findByConnectionId(connectionId).get().getBalance();

        // ACT
        HomeDto homeDto = payMyBuddyServiceImpl.getBalanceAccount(connectionId);

        // ASSERT
         assertEquals(homeDto.getBalance(), balanceAccountFromDB);
    }

    @Test
    @Commit
    void testGetBalanceAccountWithNonExistingConnectionId() {
        // ARRANGE
        int connectionId = 100;

        // ACT
        HomeDto homeDto = payMyBuddyServiceImpl.getBalanceAccount(connectionId);

        // ASSERT
        assertEquals(homeDto, null);
    }

    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'getTransactions' de la classe  payMyBuddyServiceImpl
    //*********************************************************************************************************
    @Test
    @Commit
    void testGetTransactionsWithExistingConnectionId() {
        // ARRANGE
        int connectionId = 2;
        List<Transaction> transactions = transactionRepository.findTransactionsByConnectionId(connectionId);
        String name = connectionRepository.findById(connectionId).get().getName();

        // ACT
        List<TransactionDto> transactionDtos = payMyBuddyServiceImpl.getTransactions(connectionId);

        // ASSERT
        assertEquals(transactionDtos.size(), transactions.size());
        assertEquals(transactionDtos.get(0).getName(), name);
        assertEquals(transactionDtos.get(0).getDescription(), transactions.get(0).getDescription());
        assertEquals(transactionDtos.get(0).getAmount(), transactions.get(0).getAmount());
    }

    @Test
    @Commit
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
    @Commit
    void testGetBuddiesConnectedWithExistingConnectionId() {
        // ARRANGE
        int connectionId = 2;
        List<Connection> buddiesConnected = connectionRepository.findById(connectionId).get().getBuddiesConnected();

        // ACT
        List<BuddyConnectedDto> buddiesConnectedDtos = payMyBuddyServiceImpl.getBuddiesConnected(connectionId);

        // ASSERT
        assertEquals(buddiesConnectedDtos.size(), buddiesConnected.size());
        assertEquals(buddiesConnectedDtos.get(0).getName(), buddiesConnected.get(0).getName());
        assertEquals(buddiesConnectedDtos.get(1).getName(), buddiesConnected.get(1).getName());
        assertEquals(buddiesConnectedDtos.get(2).getName(), buddiesConnected.get(2).getName());
    }

    @Test
    @Commit
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
    @Commit
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
    @Commit
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
    @Commit
    void testAddTransactionWithExistingConnectionId() {
        // ARRANGE
        int connectionId = 2;
        int connectionReceiverId = 7;
        int nbTransaction = transactionRepository.findTransactionSendersByConnectionId(connectionId).size();

        String name = connectionRepository.findById(connectionReceiverId).get().getName();
        String description = "Transaction PMB_account2 vers PMB_account7";
        float amount = 70;

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setConnectionReceiverId(connectionReceiverId);
        transactionDto.setName(name);
        transactionDto.setDescription(description);
        transactionDto.setAmount(amount);

        // ACT
        Boolean resultat = payMyBuddyServiceImpl.addTransaction(connectionId, transactionDto);

        // ASSERT
        assertTrue(resultat);
        List<Transaction> transactions = transactionRepository.findTransactionSendersByConnectionId(connectionId);

        assertEquals(transactions.size(), nbTransaction + 1);
        assertEquals(transactions.get(nbTransaction).getPmbAccountSender().getConnection().getConnectionId(), connectionId);
        assertEquals(transactions.get(nbTransaction).getPmbAccountReceiver().getConnection().getConnectionId(), connectionReceiverId);
        assertEquals(transactions.get(nbTransaction).getDescription(), description);
        assertEquals(transactions.get(nbTransaction).getAmount(), amount);
    }

    @Test
    @Commit
    void testAddTransactionWithNonExistingConnectionId() {
        // ARRANGE
        int connectionId = 100;
        int connectionReceiverId = 7;
        int nbTransaction = transactionRepository.findTransactionSendersByConnectionId(connectionId).size();

        String name = connectionRepository.findById(connectionReceiverId).get().getName();
        String description = "Transaction PMB_account2 vers PMB_account7";
        float amount = 70;

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setConnectionReceiverId(connectionReceiverId);
        transactionDto.setName(name);
        transactionDto.setDescription(description);
        transactionDto.setAmount(amount);

        // ACT, ASSERT
        assertFalse(payMyBuddyServiceImpl.addTransaction(connectionId, transactionDto));
    }


    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'getProfile' de la classe  payMyBuddyServiceImpl
    //*********************************************************************************************************
    @Test
    @Commit
    void testGetProfileWithExistingConnectionId() {
        // ARRANGE
        int connectionId = 2;
        Connection connection = connectionRepository.findById(connectionId).get();

        // ACT
        ProfileDto profileDto = payMyBuddyServiceImpl.getProfile(connectionId);

        // ASSERT
        assertNotNull(profileDto);
        assertEquals(profileDto.getConnectionId(), connectionId);
        assertEquals(profileDto.getEmail(), connection.getEmail());
        assertEquals(profileDto.getPassword(), connection.getPassword());
        assertEquals(profileDto.getName(), connection.getName());
    }

    @Test
    @Commit
    void testGetProfileWithNonExistingConnectionId() {
        // ARRANGE
        int connectionId = 100;

        // ACT
        ProfileDto profileDto = payMyBuddyServiceImpl.getProfile(connectionId);

        // ASSERT
        assertNull(profileDto);
    }


    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'updateProfile' de la classe  payMyBuddyServiceImpl
    //*********************************************************************************************************
    @Test
    @Commit
    void testUpdateProfileWithExistingConnectionId() {
        // ARRANGE
        int connectionId = 2;

        String newEmail= "connection2_test_new@gmail.com";
        String newPassword= "pwd2_new";
        String newName= "buddy2_new";

        ProfileDto profileDto = new ProfileDto();
        profileDto.setConnectionId(connectionId);
        profileDto.setEmail(newEmail);
        profileDto.setPassword(newPassword);
        profileDto.setName(newName);

        // ACT
        Boolean resultat = payMyBuddyServiceImpl.updateProfile(profileDto);

        // ASSERT
        assertTrue(resultat);

        Connection connection = connectionRepository.findById(connectionId).get();
        assertEquals(connection.getEmail(), newEmail);
        assertEquals(connection.getPassword(), newPassword);
        assertEquals(connection.getName(), newName);
    }

    @Test
    @Commit
    void testUpdateProfileWithNonExistingConnectionId() {
        // ARRANGE
        int connectionId = 100;

        String newEmail= "connection2_test_new@gmail.com";
        String newPassword= "pwd2_new";
        String newName= "buddy2_new";

        ProfileDto profileDto = new ProfileDto();
        profileDto.setConnectionId(connectionId);
        profileDto.setEmail(newEmail);
        profileDto.setPassword(newPassword);
        profileDto.setName(newName);

        // ACT
        Boolean resultat = payMyBuddyServiceImpl.updateProfile(profileDto);

        // ASSERT
        assertFalse(resultat);
    }

    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'register' de la classe  payMyBuddyServiceImpl
    //*********************************************************************************************************

    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'logint' de la classe  payMyBuddyServiceImpl
    //*********************************************************************************************************

    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'logout' de la classe  payMyBuddyServiceImpl
    //*********************************************************************************************************


}
