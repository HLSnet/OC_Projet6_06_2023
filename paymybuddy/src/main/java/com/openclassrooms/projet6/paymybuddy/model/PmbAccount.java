package com.openclassrooms.projet6.paymybuddy.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "PMB_account")
public class PmbAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int pmbAccountId;

    private float balance = 0;

    @OneToOne
    @JoinColumn(name = "connection_id")
    private Connection connection;


    @OneToMany(mappedBy = "pmbAccountSender",
               orphanRemoval = true)
    List<Transaction> receiverTransactions;

    @OneToMany(mappedBy = "pmbAccountReceiver",
               orphanRemoval = true)
    List<Transaction> senderTransactions;



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

    public PmbAccount() {
    }

    public PmbAccount(int pmbAccountId, float balance) {
        this.pmbAccountId = pmbAccountId;
        this.balance = balance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public List<Transaction> getReceiverTransactions() {
        return receiverTransactions;
    }

    public void setReceiverTransactions(List<Transaction> receiverTransactions) {
        this.receiverTransactions = receiverTransactions;
    }

    public List<Transaction> getSenderTransactions() {
        return senderTransactions;
    }

    public void setSenderTransactions(List<Transaction> senderTransactions) {
        this.senderTransactions = senderTransactions;
    }
}