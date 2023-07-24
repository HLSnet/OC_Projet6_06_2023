package com.openclassrooms.projet6.paymybuddy.model;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PMB_account")
@DynamicUpdate
public class PmbAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int pmbAccountId;

    private float balance = 0;

    @OneToOne( cascade = { CascadeType.PERSIST,
            CascadeType.MERGE
    }
    )
    @JoinColumn(name = "connection_id")
    private Connection connection;


    @OneToMany(mappedBy = "pmbAccountSender",
               cascade = {CascadeType.PERSIST,
                          CascadeType.MERGE
               },
               orphanRemoval = true
              )
    private List<Transaction> receiverTransactions= new ArrayList<>();

    @OneToMany(mappedBy = "pmbAccountReceiver",
               cascade = {CascadeType.PERSIST,
                          CascadeType.MERGE
               },
               orphanRemoval = true
                )
    private List<Transaction> senderTransactions= new ArrayList<>();



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

    public void saveConnection(Connection newConnection){
        this.setConnection(newConnection);
        newConnection.setPmbAccount(this);
    }
    public void deleteConnection(Connection newConnection){
        this.setConnection(null);
        newConnection.setPmbAccount(null);
    }

    public void addReceiverTransaction(Transaction transaction){
        receiverTransactions.add(transaction);
        transaction.getPmbAccountReceiver().getSenderTransactions().add(transaction);
    }

    public void removeReceiverTransaction(Transaction transaction){
        receiverTransactions.remove(transaction);
        transaction.getPmbAccountSender().senderTransactions.remove(transaction);
    }
}