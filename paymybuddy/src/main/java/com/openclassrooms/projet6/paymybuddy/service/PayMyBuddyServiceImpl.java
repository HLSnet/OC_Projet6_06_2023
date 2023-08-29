package com.openclassrooms.projet6.paymybuddy.service;

import com.openclassrooms.projet6.paymybuddy.dto.*;
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
     * Retrieves the balance information for a user's account based on the given connection ID.
     *
     * @param connectionId The unique identifier of the connection associated with the account.
     * @return A BalanceDto object containing the account's name and balance if the connection and account are found, else null.
     */
    @Override
    public HomeDto getBalanceAccount(int connectionId) {
        HomeDto homeDto = null;

        Optional<Connection> connection = connectionRepository.findById(connectionId);
        Optional<PmbAccount> pmbAccount = pmbAccountRepository.findById(connectionId);
        if (connection.isPresent() && pmbAccount.isPresent()) {
            homeDto = new HomeDto();
            homeDto.setName(connection.get().getName());
            homeDto.setBalance(pmbAccount.get().getBalance());
        }

        return homeDto;
    }

    /**
     * Adds the specified amount of credit to the balance of a user's account.
     *
     * @param connectionId The unique identifier of the user's connection.
     * @param amountCredit The amount of credit to be added to the account balance.
     * @return True if the credit was successfully added, false otherwise (e.g., if the user does not exist or the credit would result in a negative balance).
     */
    public boolean addToBalance(int connectionId, float amountCredit){
        boolean result = false;

        Optional<PmbAccount> pmbAccount = pmbAccountRepository.findById(connectionId);
        if (pmbAccount.isPresent()) {
            float balance = pmbAccount.get().getBalance();
            if ((amountCredit >= 0) || (balance + amountCredit) >= 0 ){
                pmbAccount.get().setBalance(balance + amountCredit);
                result= true;
            }
        }
        return result;
    }


/**
 * Retrieve the informations displayed on the transfer page for a given connection ID.
 *
 * This method retrieves a TransferDto object containing information about
 * transactions and buddies connected to the specified connection ID.
 *
 * @param connectionId The ID of the connection for which to retrieve transfer page information.
 * @return TransferDto containing a list of TransactionDto objects representing transactions
 *         and a list of BuddyConnectedDto objects representing buddies connected to the specified connection ID.
 */
    @Override
    public TransferDto getTransferPageInformations(int connectionId) {
        TransferDto transferDto = new TransferDto();

        // Gets a list of TransactionDto objects associated with the given connectionId.
        ArrayList<TransactionDto> transactionDtos = new ArrayList<>();
        ArrayList<Transaction> transactions = transactionRepository.findTransactionReceiversByConnectionId(connectionId);
        if (!transactions.isEmpty()) {
            for (Transaction    transaction : transactions) {
                TransactionDto transactionDto = new TransactionDto();

                int receiverConnectionId = transaction.getPmbAccountReceiver().getConnection().getConnectionId();
                transactionDto.setConnectionReceiverId(receiverConnectionId);
                transactionDto.setName(connectionRepository.findById(receiverConnectionId).get().getName());
                transactionDto.setDescription(transaction.getDescription());
                transactionDto.setAmount((int)transaction.getAmount());
                transactionDtos.add(transactionDto);
            }
        }

        // Gets a list of BuddyConnectedDto objects representing the buddies connected to the given connectionId.
        ArrayList<BuddyConnectedDto> buddiesConnectedDtos = new ArrayList<>();
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
        transferDto.setBuddyConnectedDtos(buddiesConnectedDtos);
        transferDto.setTransactionDtos(transactionDtos);
        return transferDto;
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
            // Evolution : ajouter à la liste des connectors connectionId
            // afin de gerer un affichage des transactions englobant en plus des débits les crédits

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
     * Gets the contact information.
     *
     * @return The contact information as a string.
     */
    @Override
    public String getContact() {
        return COORDONNEES_CONTACT;
    }


    /**
     * Adds a connection to the list of buddies connected for the given connection.
     *
     * @param connectionId The ID of the main connection.
     * @param emailBuddy   The email of the buddy to be added as a connection.
     * @return True if the buddy connection was added successfully, false otherwise.
     */
    @Override
    public boolean addBuddyConnected(int connectionId, String emailBuddy) {
        boolean result = false;

        Optional<Connection> optConnection = connectionRepository.findById(connectionId);
        if (optConnection.isPresent()) {
            Optional<Connection> optConnectionBuddyId = connectionRepository.findByEmail(emailBuddy);
            if (optConnectionBuddyId.isPresent()) {
                optConnection.get().addBuddyConnected(optConnectionBuddyId.get());
                result = true;
            }
        }
        return result;
    }


//    /**
//     * Updates the profile information for the given ProfileDto.
//     *
//     * @param profileDto The ProfileDto containing the updated profile information.
//     * @return True if the profile was successfully updated, False otherwise.
//     *         The method returns False if the Connection with profileDto.getConnectionId()
//     *         is not found.
//     */
//    @Override
//    public boolean updateProfile(ProfileDto profileDto) {
//        boolean result = false;
//        Optional<Connection> optConnection = connectionRepository.findById(profileDto.getConnectionId());
//
//        if (optConnection.isPresent()) {
//            optConnection.get().setEmail(profileDto.getEmail());
//            optConnection.get().setPassword(profileDto.getPassword());
//            optConnection.get().setName(profileDto.getName());
//
//            result = true;
//        }
//        return result;
//    }





    //    /**
//     * Gets a list of TransactionDto objects associated with the given connectionId.
//     *
//     * @param connectionId The ID of the Connection to retrieve the transactions for.
//     * @return A list of TransactionDto objects containing transaction details for the connectionId.
//     *         If no transactions are found, an empty list is returned.
//     */
//    @Override
//    public List<TransactionDto> getTransactions(int connectionId) {
//        List<TransactionDto> transactionDtos = new ArrayList<>();
//        List<Transaction> transactions = transactionRepository.findTransactionReceiversByConnectionId(connectionId);
//
//
//        if (!transactions.isEmpty()) {
//            for (Transaction    transaction : transactions) {
//                TransactionDto transactionDto = new TransactionDto();
//
//                int receiverConnectionId = transaction.getPmbAccountReceiver().getConnection().getConnectionId();
//                transactionDto.setConnectionReceiverId(receiverConnectionId);
//                transactionDto.setName(connectionRepository.findById(receiverConnectionId).get().getName());
//                transactionDto.setDescription(transaction.getDescription());
//                transactionDto.setAmount(transaction.getAmount());
//                transactionDtos.add(transactionDto);
//            }
//        }
//        return transactionDtos;
//    }
//
//    /**
//     * Gets a list of BuddyConnectedDto objects representing the buddies connected to the given connectionId.
//     *
//     * @param connectionId The ID of the Connection for which to retrieve the connected buddies.
//     * @return A list of BuddyConnectedDto objects containing information about the connected buddies.
//     *         If the Connection with the given connectionId is not found, an empty list is returned.
//     */
//    @Override
//    public List<BuddyConnectedDto> getBuddiesConnected(int connectionId) {
//        List<BuddyConnectedDto> buddiesConnectedDtos = new ArrayList<>();
//
//        Optional<Connection> optConnection = connectionRepository.findById(connectionId);
//        if (optConnection.isPresent()) {
//            List<Connection> buddiesConnected = optConnection.get().getBuddiesConnected();
//            for (Connection connection : buddiesConnected) {
//                BuddyConnectedDto buddiesConnectedDto = new BuddyConnectedDto();
//                buddiesConnectedDto.setConnectionId(connection.getConnectionId());
//                buddiesConnectedDto.setName(connection.getName());
//                buddiesConnectedDtos.add(buddiesConnectedDto);
//            }
//        }
//        return buddiesConnectedDtos;
//    }

}
