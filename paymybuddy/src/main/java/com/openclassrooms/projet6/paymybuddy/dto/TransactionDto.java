package com.openclassrooms.projet6.paymybuddy.dto;



public class TransactionDto {
    private int connectionReceiverId;
    private String name;
    private String description;
    private float amount;

    public int getConnectionReceiverId() {
        return connectionReceiverId;
    }
    public void setConnectionReceiverId(int connectionReceiverId) {
        this.connectionReceiverId = connectionReceiverId;
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
