package com.openclassrooms.projet6.paymybuddy.service;

import com.openclassrooms.projet6.paymybuddy.dto.BuddyConnectedDto;
import com.openclassrooms.projet6.paymybuddy.dto.ProfileDto;
import com.openclassrooms.projet6.paymybuddy.dto.TransactionDto;
import com.openclassrooms.projet6.paymybuddy.model.Connection;
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

    @Override
    public float getBalanceAccount(int connectionId) {
        return 0;
    }

    @Override
    public List<TransactionDto> getTransactions(int connectionId) {
        return null;
    }

    @Override
    public List<BuddyConnectedDto> getBuddiesConnected(int connectionId) {

        return null;
    }

    @Override
    public boolean addConnection(int connectionId, int  connectionBuddyId) {
        return false;
    }

    @Override
    public boolean addTransaction(int connectionId, TransactionDto transactionDto) {
        return false;
    }

    @Override
    public String getContact() {
        return COORDONNEES_CONTACT;
    }

    @Override
    public ProfileDto getProfile(int connectionId) {
        return null;
    }

    @Override
    public boolean updateProfile(ProfileDto profileDto) {
        return false;
    }


    @Override
    public boolean register(int connectionId) {
        return false;
    }

    @Override
    public boolean logint(int connectionId) {
        return false;
    }

    @Override
    public boolean logout(int connectionId) {
        return false;
    }



//    public List<ConnectionDto> getConnections() {
//        List<Connection> connections = connectionRepository.findAll();
//
//        List<ConnectionDto> connectionDtos = new ArrayList<>();
//        for (Connection connection: connections ) {
//            ConnectionDto connectionDto = new ConnectionDto();
//            connectionDto.setUserId(connection.getConnectionId());
//            connectionDto.setEmail(connection.getEmail());
//            connectionDto.setPassword(connection.getPassword());
//            connectionDto.setPmbAccount(connection.getPmbAccount());
//            connectionDtos.add(connectionDto);
//        }
//        return connectionDtos;
//    }
//
//
//    public ConnectionDto getConnectionById(Integer id) {
//        Connection connection = connectionRepository.findById(id).get();
//
//        ConnectionDto connectionDto = new ConnectionDto();
//
//        connectionDto.setUserId(connection.getConnectionId());
//        connectionDto.setEmail(connection.getEmail());
//        connectionDto.setPassword(connection.getPassword());
//        connectionDto.setPmbAccount(connection.getPmbAccount());
//
//        return connectionDto;
//    }
//
//
//    public List<PmbAccountDto> getPmbAccounts(){
//        List<PmbAccount> pmbAccounts = pmbAccountRepository.findAll();
//
//        List<PmbAccountDto> pmbAccountDtos = new ArrayList<>();
//        for (PmbAccount pmbAccount: pmbAccounts ) {
//            PmbAccountDto pmbAccountDto = new PmbAccountDto();
//            pmbAccountDto.setPmbAccountId(pmbAccount.getPmbAccountId());
//            pmbAccountDto.setBalance(pmbAccount.getBalance());
//            pmbAccountDto.setConnection(pmbAccount.getConnection());
//            pmbAccountDtos.add(pmbAccountDto);
//        }
//        return pmbAccountDtos;
//    }
//
//
//    public PmbAccountDto getPmbAccountById(Integer id) {
//        PmbAccount pmbAccount = pmbAccountRepository.findById(id).get();
//
//        PmbAccountDto pmbAccountDto = new PmbAccountDto();
//
//        pmbAccountDto.setBalance(pmbAccount.getBalance());
//        pmbAccountDto.setConnection(pmbAccount.getConnection());
//
//        return pmbAccountDto;
//    }

}
