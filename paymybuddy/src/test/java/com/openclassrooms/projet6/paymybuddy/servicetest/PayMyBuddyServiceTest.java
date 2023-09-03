package com.openclassrooms.projet6.paymybuddy.servicetest;

import com.openclassrooms.projet6.paymybuddy.dto.*;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;


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

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     *  Tests unitaires de la méthode 'registration' de la classe  payMyBuddyServiceImpl
     *
     *   public boolean registration(String email, String name, String password);
     */
    @Test
    @Commit
    void testRegistrationWithNonExistingConnectionId() {
        // ARRANGE
        String email= "connection10_test@gmail.com";
        String password= "pwd10";
        String name= "buddy10";

        // ACT
        boolean result = payMyBuddyServiceImpl.registration(email, name, password);

        // ASSERT
        assertTrue(result == true);
        Optional<Connection> connectionCreatedOpt = connectionRepository.findByEmail(email);

        assertTrue(connectionCreatedOpt.isPresent());
        Connection connectionCreated = connectionCreatedOpt.get();

        assertEquals(email, connectionCreated.getEmail());
        assertTrue(passwordEncoder.matches(password, connectionCreated.getPassword()));
        assertEquals(name, connectionCreated.getName());
    }

    @Test
    @Commit
    void testRegistrationWithExistingConnectionId() {
        // ARRANGE
        String email= "connection2_test@gmail.com";
        String password= "pwd2";
        String name= "buddy2";

        // ACT
        boolean result = payMyBuddyServiceImpl.registration(email, name, password);

        // ASSERT
        assertTrue(result == false);
    }



    /**
     *  Tests unitaires de la méthode 'getBalanceAccount' de la classe  payMyBuddyServiceImpl
     *
     *    public HomeDto getBalanceAccount(int connectionId);
     *
     *    @see HomeDto Cliquez ici pour accéder à la documentation de la classe HomeDto.
     */
    @Test
    @Commit
    void testGetBalanceAccountWithExistingConnectionId() {
        // ARRANGE
        int connectionId = 2;
        String name = "buddy2";
        float balanceAccountFromDB = pmbAccountRepository.findByConnectionId(connectionId).get().getBalance();

        // ACT
        HomeDto homeDto = payMyBuddyServiceImpl.getBalanceAccount(connectionId);

        // ASSERT
        assertEquals(homeDto.getBalance(), balanceAccountFromDB);
        assertEquals(homeDto.getName(), name);
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



    /**
     *  Tests unitaires de la méthode 'addToBalance' de la classe  payMyBuddyServiceImpl
     *
     *    public boolean addToBalance(int connectionId, float amountCredit);
     */
    @Test
    @Commit
    void testAddToBalance() {
        // ARRANGE
        int connectionId = 2;
        float amountCredit = 100;

        float balanceAccountFromDbBefore = pmbAccountRepository.findByConnectionId(connectionId).get().getBalance();

        // ACT
        boolean result = payMyBuddyServiceImpl.addToBalance(connectionId, amountCredit);
        float balanceAccountFromDbAfter = pmbAccountRepository.findByConnectionId(connectionId).get().getBalance();

        // ASSERT
        assertTrue(balanceAccountFromDbAfter == balanceAccountFromDbBefore + amountCredit);
    }



    /**
    *  Tests unitaires de la méthode 'getTransferPageInformations' de la classe  payMyBuddyServiceImpl
    *
    *    public TransferDto getTransferPageInformations(int connectionId);
    *
    * @see TransferDto Cliquez ici pour accéder à la documentation de la classe TransferDto.
   */
    @Test
    @Commit
    void testGetTransferPageInformationsWithExistingConnectionId() {
        // ARRANGE
        int connectionSenderId = 2;
        List<Transaction> transactions = transactionRepository.findTransactionReceiversByConnectionId(connectionSenderId);
        int nbTransactions =  transactions.size();

        List<Connection> buddiesConnected = connectionRepository.findById(connectionSenderId).get().getBuddiesConnected();
        int nbBuddies =  buddiesConnected.size();

        // ACT
        TransferDto transferDto = payMyBuddyServiceImpl.getTransferPageInformations(connectionSenderId);

        // ASSERT
        assertEquals(transferDto.getTransactionDtos().size(), nbTransactions);

        for (int i =0; i < nbTransactions; i++){
            int connectionReceiverId = transactions.get(i).getPmbAccountReceiver().getConnection().getConnectionId();
            assertEquals(transferDto.getBuddyConnectedDtos().get(i).getName(), connectionRepository.findById(connectionReceiverId).get().getName());
            assertEquals(transferDto.getTransactionDtos().get(i).getDescription(), transactions.get(i).getDescription());
            assertEquals(transferDto.getTransactionDtos().get(i).getAmount(), transactions.get(i).getAmount());
        }

        assertEquals(transferDto.getBuddyConnectedDtos().size(), nbBuddies);
        for (int i =0; i < nbBuddies; i++){
            assertEquals(transferDto.getBuddyConnectedDtos().get(i).getName(), buddiesConnected.get(i).getName());
        }
    }

    

    /**
     *  Tests unitaires de la méthode 'addTransaction' de la classe  payMyBuddyServiceImpl
     *
     *   public boolean addTransaction(int connectionId, TransactionDto transactionDto);
     */
    @Test
    @Commit
    void testAddTransactionWithExistingConnectionId() {
        // ARRANGE
        int connectionSenderId = 2;
        int connectionReceiverId = 7;
        int nbTransaction = transactionRepository.findTransactionReceiversByConnectionId(connectionSenderId).size();

        String name = connectionRepository.findById(connectionReceiverId).get().getName();
        String description = "Transaction PMB_account2 vers PMB_account7";
        int amount = 70;

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setConnectionReceiverId(connectionReceiverId);
        transactionDto.setName(name);
        transactionDto.setDescription(description);
        transactionDto.setAmount(amount);

        // ACT
        Boolean resultat = payMyBuddyServiceImpl.addTransaction(connectionSenderId, transactionDto);

        // ASSERT
        assertTrue(resultat);
        List<Transaction> transactions = transactionRepository.findTransactionReceiversByConnectionId(connectionSenderId);

        assertEquals(transactions.size(), nbTransaction + 1);
        assertEquals(transactions.get(nbTransaction).getPmbAccountSender().getConnection().getConnectionId(), connectionSenderId);
        assertEquals(transactions.get(nbTransaction).getPmbAccountReceiver().getConnection().getConnectionId(), connectionReceiverId);
        assertEquals(transactions.get(nbTransaction).getDescription(), description);
        assertEquals(transactions.get(nbTransaction).getAmount(), amount);
    }



    /**
     *  Tests unitaires de la méthode 'addBuddyConnected' de la classe  payMyBuddyServiceImpl
     *
     *   public boolean addBuddyConnected(int connectionId, String emailBuddy);
     */
    @Test
    @Commit
    void testAddBuddyConnectedWithExistingConnectionId() {
        // ARRANGE
        int connectionId = 2;
        String emailBuddy = "connection2_test@gmail.com";

        // ACT
        boolean result = payMyBuddyServiceImpl.addBuddyConnected(connectionId, emailBuddy);

        // ASSERT
        assertTrue(result);
        Connection connection = connectionRepository.findById(connectionId).get();
        Connection connectionBuddy = connectionRepository.findByEmail(emailBuddy).get();
        assertTrue(connection.getBuddiesConnected().contains(connectionBuddy));
    }

    @Test
    @Commit
    void testAddBuddyConnectedWithNonExistingConnectionId() {
        // ARRANGE
        int connectionId = 100;
        String emailBuddy = "connection7@gmail.com";

        // ACT, ASSERT
        assertFalse(payMyBuddyServiceImpl.addBuddyConnected(connectionId, emailBuddy));
    }



    /**
     *  Tests unitaires de la méthode 'getProfile' de la classe  payMyBuddyServiceImpl
     *
     *    public ProfileDto getProfile(int connectionId);
     *
     * @see ProfileDto Cliquez ici pour accéder à la documentation de la classe ProfileDto.
     */
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
}
