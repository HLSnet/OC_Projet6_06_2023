package com.openclassrooms.projet6.paymybuddy.service;


import com.openclassrooms.projet6.paymybuddy.dto.ConnectionDto;
import com.openclassrooms.projet6.paymybuddy.dto.TransactionDto;


import java.util.List;

public interface PayMyBuddyService {
    boolean register(int identifiant);
    boolean logint(int identifiant);
    boolean logout(int identifiant);
    List<TransactionDto> getTransactions(int identifiant);
    boolean addTransaction(TransactionDto transactionDto);
    List<ConnectionDto> getBuddiesConnected(int identifiant);
    ConnectionDto addConnection(ConnectionDto connectionDto);
    boolean updateConnection(ConnectionDto connectionDto);
    float getBalanceAccount();
    String getContact();
    public ConnectionDto getProfile(Integer id);
}
