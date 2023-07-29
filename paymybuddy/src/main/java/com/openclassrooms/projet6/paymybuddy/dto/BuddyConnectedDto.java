package com.openclassrooms.projet6.paymybuddy.dto;

public class BuddyConnectedDto {
    private int connectionId;
    private String name;

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

    @Override
    public String toString() {
        return "BuddyConnectedDto{" +
                "connectionId=" + connectionId +
                ", name='" + name + '\'' +
                '}';
    }
}
