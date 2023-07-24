package com.openclassrooms.projet6.paymybuddy.service;

import com.openclassrooms.projet6.paymybuddy.dto.ConnectionDto;
import com.openclassrooms.projet6.paymybuddy.dto.PmbAccountDto;
import com.openclassrooms.projet6.paymybuddy.dto.TransactionDto;
import com.openclassrooms.projet6.paymybuddy.model.Connection;
import com.openclassrooms.projet6.paymybuddy.model.PmbAccount;
import com.openclassrooms.projet6.paymybuddy.repository.ConnectionRepository;
import com.openclassrooms.projet6.paymybuddy.repository.PmbAccountRepository;
import com.openclassrooms.projet6.paymybuddy.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
    public boolean register(int identifiant) {
        return false;
    }

    @Override
    public boolean logint(int identifiant) {
        return false;
    }

    @Override
    public boolean logout(int identifiant) {
        return false;
    }

    @Override
    public List<TransactionDto> getTransactions(int identifiant) {
        return null;
    }

    @Override
    public boolean addTransaction(TransactionDto transactionDto) {
        return false;
    }

    @Override
    public List<ConnectionDto> getBuddiesConnected(int identifiant) {
        List<ConnectionDto> connectionDtos = new ArrayList<>();

        Optional<Connection> optConnection = connectionRepository.findById(identifiant);
        if (!optConnection.isEmpty()) {
            List<Connection> buddiesConnected = optConnection.get().getBuddiesConnected();
            for (Connection connection : buddiesConnected) {
                ConnectionDto connectionDto = new ConnectionDto();
                connectionDto.setUserId(connection.getConnectionId());
                connectionDto.setEmail(connection.getEmail());
                connectionDto.setPassword(connection.getPassword());
                connectionDto.setPmbAccount(connection.getPmbAccount());
                connectionDto.setBuddiesConnected(connection.getBuddiesConnected());
                connectionDto.setBuddiesConnector(connection.getBuddiesConnector());
                connectionDtos.add(connectionDto);
            }
        }
        return connectionDtos;
    }

    @Override
    public ConnectionDto addConnection(ConnectionDto connectionDto) {
        return null;
    }

    @Override
    public boolean updateConnection(ConnectionDto connectionDto) {
        return false;
    }

    @Override
    public float getBalanceAccount() {
        return 0;
    }

    @Override
    public String getContact() {
        return "Merci de nous joindre par tel au 0655443322 ou par e-mail : paymybuddy@pmb.com.";
    }

    @Override
    public ConnectionDto getProfile(Integer id) {
        return null;
    }



    public List<ConnectionDto> getConnections() {
        List<Connection> connections = connectionRepository.findAll();

        List<ConnectionDto> connectionDtos = new ArrayList<>();
        for (Connection connection: connections ) {
            ConnectionDto connectionDto = new ConnectionDto();
            connectionDto.setUserId(connection.getConnectionId());
            connectionDto.setEmail(connection.getEmail());
            connectionDto.setPassword(connection.getPassword());
            connectionDto.setPmbAccount(connection.getPmbAccount());
            connectionDtos.add(connectionDto);
        }
        return connectionDtos;
    }


    public ConnectionDto getConnectionById(Integer id) {
        Connection connection = connectionRepository.findById(id).get();

        ConnectionDto connectionDto = new ConnectionDto();

        connectionDto.setUserId(connection.getConnectionId());
        connectionDto.setEmail(connection.getEmail());
        connectionDto.setPassword(connection.getPassword());
        connectionDto.setPmbAccount(connection.getPmbAccount());

        return connectionDto;
    }


    public List<PmbAccountDto> getPmbAccounts(){
        List<PmbAccount> pmbAccounts = pmbAccountRepository.findAll();

        List<PmbAccountDto> pmbAccountDtos = new ArrayList<>();
        for (PmbAccount pmbAccount: pmbAccounts ) {
            PmbAccountDto pmbAccountDto = new PmbAccountDto();
            pmbAccountDto.setPmbAccountId(pmbAccount.getPmbAccountId());
            pmbAccountDto.setBalance(pmbAccount.getBalance());
            pmbAccountDto.setConnection(pmbAccount.getConnection());
            pmbAccountDtos.add(pmbAccountDto);
        }
        return pmbAccountDtos;
    }


    public PmbAccountDto getPmbAccountById(Integer id) {
        PmbAccount pmbAccount = pmbAccountRepository.findById(id).get();

        PmbAccountDto pmbAccountDto = new PmbAccountDto();

        pmbAccountDto.setBalance(pmbAccount.getBalance());
        pmbAccountDto.setConnection(pmbAccount.getConnection());

        return pmbAccountDto;
    }

}
