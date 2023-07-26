package com.openclassrooms.projet6.paymybuddy.dto;



public class TransactionDto {
    private int connectionId;
    private String name;
    private String description;
    private float amount;

    public int getConnectionId() {
        return connectionId;
    }
    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }
}
