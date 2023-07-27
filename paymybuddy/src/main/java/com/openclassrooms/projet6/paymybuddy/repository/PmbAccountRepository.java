package com.openclassrooms.projet6.paymybuddy.repository;

import com.openclassrooms.projet6.paymybuddy.model.PmbAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PmbAccountRepository extends JpaRepository<PmbAccount, Integer> {
    @Query(value = "SELECT * FROM PMB_account WHERE connection_id = :id", nativeQuery = true)
    public Optional<PmbAccount>  findByConnectionId(@Param("id") Integer id);

    @Query(value = "SELECT balance FROM PMB_account WHERE connection_id = :id", nativeQuery = true)
    public float  getBalanceByConnectionId(@Param("id") Integer id);



}
