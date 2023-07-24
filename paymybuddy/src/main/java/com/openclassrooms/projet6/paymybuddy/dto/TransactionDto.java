package com.openclassrooms.projet6.paymybuddy.dto;

import com.openclassrooms.projet6.paymybuddy.model.PmbAccount;


public class TransactionDto {
    private int transactionId;

    private float amount;

    private String description;

    private PmbAccount pmbAccountSender;

    private PmbAccount pmbAccountReceiver;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PmbAccount getPmbAccountSender() {
        return pmbAccountSender;
    }

    public void setPmbAccountSender(PmbAccount pmbAccountSender) {
        this.pmbAccountSender = pmbAccountSender;
    }

    public PmbAccount getPmbAccountReceiver() {
        return pmbAccountReceiver;
    }

    public void setPmbAccountReceiver(PmbAccount pmbAccountReceiver) {
        this.pmbAccountReceiver = pmbAccountReceiver;
    }
}
