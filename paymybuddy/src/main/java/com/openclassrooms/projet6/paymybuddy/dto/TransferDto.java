package com.openclassrooms.projet6.paymybuddy.dto;

import java.util.ArrayList;

public class TransferDto {
    private ArrayList<TransactionDto> transactionDtos = new ArrayList<>();

    public ArrayList<TransactionDto> getTransactionDtos() {
        return transactionDtos;
    }

    public void setTransactionDtos(ArrayList<TransactionDto> transactionDtos) {
        this.transactionDtos = transactionDtos;
    }
}
