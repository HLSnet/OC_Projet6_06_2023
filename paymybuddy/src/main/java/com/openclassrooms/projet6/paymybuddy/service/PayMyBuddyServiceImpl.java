package com.openclassrooms.projet6.paymybuddy.service;

import com.openclassrooms.projet6.paymybuddy.dto.BuddyConnectedDto;
import com.openclassrooms.projet6.paymybuddy.dto.ProfileDto;
import com.openclassrooms.projet6.paymybuddy.dto.TransactionDto;
import com.openclassrooms.projet6.paymybuddy.model.Connection;
import com.openclassrooms.projet6.paymybuddy.model.PmbAccount;
import com.openclassrooms.projet6.paymybuddy.model.Transaction;
import com.openclassrooms.projet6.paymybuddy.repository.ConnectionRepository;
import com.openclassrooms.projet6.paymybuddy.repository.PmbAccountRepository;
import com.openclassrooms.projet6.paymybuddy.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.openclassrooms.projet6.paymybuddy.constants.Constants.COORDONNEES_CONTACT;
import static com.openclassrooms.projet6.paymybuddy.constants.Constants.NON_EXISTING_ACCOUNT;


@Service
@Transactional
public class PayMyBuddyServiceImpl implements PayMyBuddyService{
    @Autowired
    ConnectionRepository connectionRepository;

    @Autowired
    private PmbAccountRepository pmbAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Gets the balance of the PmbAccount associated with the given connectionId.
     *
     * @param connectionId The ID of the Connection to retrieve the balance for.
     * @return The balance of the PmbAccount associated with the connectionId.
     *         If the PmbAccount is not found, returns the value NON_EXISTING_ACCOUNT.
     */
    @Override
    public float getBalanceAccount(int connectionId) {
        float balanceAccount = NON_EXISTING_ACCOUNT;

        Optional<PmbAccount> pmbAccount = pmbAccountRepository.findByConnectionId(connectionId);
        if (pmbAccount.isPresent()) {
            balanceAccount= pmbAccount.get().getBalance();
        }
        return balanceAccount;
    }

    /**
     * Gets a list of TransactionDto objects associated with the given connectionId.
     *
     * @param connectionId The ID of the Connection to retrieve the transactions for.
     * @return A list of TransactionDto objects containing transaction details for the connectionId.
     *         If no transactions are found, an empty list is returned.
     */
    @Override
    public List<TransactionDto> getTransactions(int connectionId) {
        List<TransactionDto> transactionDtos = new ArrayList<>();
        List<Transaction> transactions = transactionRepository.findTransactionsByConnectionId(connectionId);


        if (!transactions.isEmpty()) {
            for (Transaction    transaction : transactions) {
                TransactionDto transactionDto = new TransactionDto();

                transactionDto.setConnectionReceiverId(connectionId);
                transactionDto.setName(connectionRepository.findById(connectionId).get().getName());
                transactionDto.setDescription(transaction.getDescription());
                transactionDto.setAmount(transaction.getAmount());
                transactionDtos.add(transactionDto);
            }
        }
        return transactionDtos;
    }

    /**
     * Gets a list of BuddyConnectedDto objects representing the buddies connected to the given connectionId.
     *
     * @param connectionId The ID of the Connection for which to retrieve the connected buddies.
     * @return A list of BuddyConnectedDto objects containing information about the connected buddies.
     *         If the Connection with the given connectionId is not found, an empty list is returned.
     */
    @Override
    public List<BuddyConnectedDto> getBuddiesConnected(int connectionId) {
        List<BuddyConnectedDto> buddiesConnectedDtos = new ArrayList<>();

        Optional<Connection> optConnection = connectionRepository.findById(connectionId);
        if (optConnection.isPresent()) {
            List<Connection> buddiesConnected = optConnection.get().getBuddiesConnected();
            for (Connection connection : buddiesConnected) {
                BuddyConnectedDto buddiesConnectedDto = new BuddyConnectedDto();
                buddiesConnectedDto.setConnectionId(connection.getConnectionId());
                buddiesConnectedDto.setName(connection.getName());
                buddiesConnectedDtos.add(buddiesConnectedDto);
            }
        }
        return buddiesConnectedDtos;
    }

    /**
     * Adds a new buddy connection between the given connectionId and connectionBuddyId.
     *
     * @param connectionId      The ID of the Connection to add the buddy connection for.
     * @param connectionBuddyId The ID of the buddy Connection to be added as a buddy.
     * @return True if the buddy connection was successfully added, False otherwise.
     *         The method returns False if either the Connection with connectionId or connectionBuddyId is not found.
     */
    @Override
    public boolean addBuddyConnected(int connectionId, int  connectionBuddyId) {
        boolean result = false;
        Optional<Connection> optConnection = connectionRepository.findById(connectionId);

        if (optConnection.isPresent()) {
            optConnection.get().addBuddyConnected(connectionRepository.findById(connectionBuddyId).get());
            result = true;
        }
        return result;
    }


    /**
     * Adds a new transaction for the given connectionId using the details from the TransactionDto.
     *
     * @param connectionId  The ID of the Connection to add the transaction for.
     * @param transactionDto The TransactionDto object containing transaction details.
     * @return True if the transaction was successfully added, False otherwise.
     *         The method returns False if the Connection with connectionId or the PmbAccount
     *         associated with connectionId or transactionDto.getConnectionReceiverId() is not found.
     */
    @Override
    public boolean addTransaction(int connectionId, TransactionDto transactionDto) {
        boolean result = false;
        Optional<Connection> optConnection = connectionRepository.findById(connectionId);

        if (optConnection.isPresent()) {
            optConnection.get().addBuddyConnected(connectionRepository.findById(connectionId).get());

            Transaction newTransaction = new Transaction();
            newTransaction.setPmbAccountSender(pmbAccountRepository.findByConnectionId(connectionId).get());
            newTransaction.setPmbAccountReceiver(pmbAccountRepository.findByConnectionId(transactionDto.getConnectionReceiverId()).get());
            newTransaction.setAmount(transactionDto.getAmount());
            newTransaction.setDescription(transactionDto.getDescription());

            Transaction transaction  = transactionRepository.save(newTransaction);

            result = true;
        }
        return result;
    }

    /**
     * Gets the contact information.
     *
     * @return The contact information as a string.
     */
    @Override
    public String getContact() {
        return COORDONNEES_CONTACT;
    }


    /**
     * Gets the ProfileDto for the given connectionId.
     *
     * @param connectionId The ID of the Connection to retrieve the profile for.
     * @return The ProfileDto object containing the profile details for the connectionId.
     *         If the Connection with the given connectionId is not found, returns null.
     */
    @Override
    public ProfileDto getProfile(int connectionId) {
        ProfileDto profileDto = null;
        Optional<Connection> optConnection = connectionRepository.findById(connectionId);

        if (optConnection.isPresent()) {
            profileDto = new ProfileDto();
            profileDto.setConnectionId(connectionId);
            profileDto.setEmail(optConnection.get().getEmail());
            profileDto.setPassword(optConnection.get().getPassword());
            profileDto.setName(optConnection.get().getName());
        }
        return profileDto;
    }


    /**
     * Updates the profile information for the given ProfileDto.
     *
     * @param profileDto The ProfileDto containing the updated profile information.
     * @return True if the profile was successfully updated, False otherwise.
     *         The method returns False if the Connection with profileDto.getConnectionId()
     *         is not found.
     */
    @Override
    public boolean updateProfile(ProfileDto profileDto) {
        boolean result = false;
        Optional<Connection> optConnection = connectionRepository.findById(profileDto.getConnectionId());

        if (optConnection.isPresent()) {
            optConnection.get().setEmail(profileDto.getEmail());
            optConnection.get().setPassword(profileDto.getPassword());
            optConnection.get().setName(profileDto.getName());

            result = true;
        }
        return result;
    }


    @Override
    public boolean register(int connectionId) {
        boolean result = false;

        return result;
    }

    @Override
    public boolean logint(int connectionId) {
        boolean result = false;

        return result;
    }

    @Override
    public boolean logout(int connectionId) {
        boolean result = false;

        return result;
    }

}
