package com.openclassrooms.projet6.paymybuddy.dto;

import com.openclassrooms.projet6.paymybuddy.model.Connection;
import com.openclassrooms.projet6.paymybuddy.model.PmbAccount;


import java.util.List;

public class ConnectionDto {
    private int userId;

    private String email;

    private String password;

    private PmbAccount pmbAccount;

    private List<Connection> buddiesConnected;

    private List<Connection> buddiesConnector;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PmbAccount getPmbAccount() {
        return pmbAccount;
    }

    public void setPmbAccount(PmbAccount pmbAccount) {
        this.pmbAccount = pmbAccount;
    }

    public List<Connection> getBuddiesConnected() {
        return buddiesConnected;
    }

    public void setBuddiesConnected(List<Connection> buddiesConnected) {
        this.buddiesConnected = buddiesConnected;
    }

    public List<Connection> getBuddiesConnector() {
        return buddiesConnector;
    }

    public void setBuddiesConnector(List<Connection> buddiesConnector) {
        this.buddiesConnector = buddiesConnector;
    }

}
