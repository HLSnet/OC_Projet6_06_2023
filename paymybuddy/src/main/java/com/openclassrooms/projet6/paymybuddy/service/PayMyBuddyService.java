package com.openclassrooms.projet6.paymybuddy.service;


import com.openclassrooms.projet6.paymybuddy.dto.*;


import java.util.List;

public interface PayMyBuddyService {
    public boolean registration(String email, String name, String password);
    public HomeDto getBalanceAccount(int connectionId);
    public boolean addToBalance(int connectionId, float amountCredit);
    public TransferDto getTransferPageInformations(int connectionId);
    public boolean addTransaction(int connectionId, TransactionDto transactionDto);
    public boolean addBuddyConnected(int connectionId, String emailBuddy);
    public String getContact();
    public ProfileDto getProfile(int connectionId);
}
