package com.openclassrooms.projet6.paymybuddy.dto;

import com.openclassrooms.projet6.paymybuddy.model.Connection;


public class PmbAccountDto {
    private int pmbAccountId;

    private float balance = 0;

    private Connection connection;

    public int getPmbAccountId() {
        return pmbAccountId;
    }

    public void setPmbAccountId(int pmbAccountId) {
        this.pmbAccountId = pmbAccountId;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


}
