package com.openclassrooms.projet6.paymybuddy.repository;

import com.openclassrooms.projet6.paymybuddy.model.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConnectionRepository extends JpaRepository<Connection, Integer> {

    public Optional<Connection> findByEmail(String email);

}
