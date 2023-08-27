package com.openclassrooms.projet6.paymybuddy.dto;

import java.util.ArrayList;

public class TransferDto {
    private ArrayList<TransactionDto> transactionDtos = new ArrayList<>();

    private ArrayList<BuddyConnectedDto> buddyConnectedDtos = new ArrayList<>();

    public ArrayList<BuddyConnectedDto> getBuddyConnectedDtos() {
        return buddyConnectedDtos;
    }

    public void setBuddyConnectedDtos(ArrayList<BuddyConnectedDto> buddyConnectedDtos) {
        this.buddyConnectedDtos = buddyConnectedDtos;
    }

    public ArrayList<TransactionDto> getTransactionDtos() {
        return transactionDtos;
    }

    public void setTransactionDtos(ArrayList<TransactionDto> transactionDtos) {
        this.transactionDtos = transactionDtos;
    }
}
