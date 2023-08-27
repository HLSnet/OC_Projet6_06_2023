package com.openclassrooms.projet6.paymybuddy.service;


import com.openclassrooms.projet6.paymybuddy.dto.*;


import java.util.List;

public interface PayMyBuddyService {

    public HomeDto getBalanceAccount(int connectionId);

    public TransferDto getTransferPageInformations(int connectionId);


//    public List<TransactionDto> getTransactions(int connectionId);
//    public List<BuddyConnectedDto> getBuddiesConnected(int connectionId);


    public boolean addBuddyConnected(int connectionId, int  connectionBuddyId);
    public boolean addTransaction(int connectionId, TransactionDto transactionDto);
    public String getContact();
    public ProfileDto getProfile(int connectionId);
    public boolean updateProfile(ProfileDto profileDto);

    public boolean register(int connectionId);
    public boolean logint(int connectionId);
    public boolean logout(int connectionId);

    public boolean addToBalance(int connectionId, float amountCredit);
}
