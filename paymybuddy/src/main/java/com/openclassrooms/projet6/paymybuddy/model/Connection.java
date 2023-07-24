package com.openclassrooms.projet6.paymybuddy.model;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "connection")
@DynamicUpdate
public class Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int connectionId;

    private String email;

    private String password;

    @OneToOne(mappedBy = "connection",
            cascade = { CascadeType.PERSIST,
                    CascadeType.MERGE
            },
             orphanRemoval = true
             )
    private PmbAccount pmbAccount;

    @ManyToMany( cascade = { CascadeType.PERSIST,
                            CascadeType.MERGE
                          }

                )
    @JoinTable(name = "connection_buddies",
               joinColumns = @JoinColumn(name = "connection_id"),
               inverseJoinColumns = @JoinColumn(name = "connection_id1")
              )
    private List<Connection> buddiesConnected= new ArrayList<>();


    @ManyToMany(mappedBy = "buddiesConnected",
                cascade = { CascadeType.PERSIST,
                            CascadeType.MERGE
                            }
    )
    private List<Connection> buddiesConnector= new ArrayList<>();


    public int getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
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

    public void addBuddyConnected(Connection newBuddyConnected){
        buddiesConnected.add(newBuddyConnected);
        newBuddyConnected.getBuddiesConnector().add(this);
    }

    public void removeBuddiesConnector(){
        for (Connection connector : buddiesConnector) {
            connector.getBuddiesConnected().remove(this);
        }
    }
}
