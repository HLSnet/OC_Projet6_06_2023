package com.openclassrooms.projet6.paymybuddy.repository;


import com.openclassrooms.projet6.paymybuddy.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query(value = "SELECT transaction.* FROM transaction  LEFT JOIN  PMB_account ON transaction.PMB_account_id = PMB_account.connection_id WHERE PMB_account.connection_id = :id", nativeQuery = true)
    public List<Transaction> findTransactionSendersByConnectionId(@Param("id") Integer id);

    @Query(value = "SELECT transaction.* FROM transaction  LEFT JOIN  PMB_account ON transaction.PMB_account_id1 = PMB_account.connection_id WHERE PMB_account.connection_id = :id", nativeQuery = true)
    public List<Transaction>  findTransactionReceiversByConnectionId(@Param("id") Integer id);

    @Query(value =  "SELECT transaction.* " +
                    "FROM transaction " +
                    "LEFT JOIN PMB_account " +
                    "ON transaction.PMB_account_id = PMB_account.connection_id " +
                    "WHERE transaction.PMB_account_id = :id "+
                    "OR transaction.PMB_account_id1 = :id", nativeQuery = true)
    public List<Transaction>  findTransactionsByConnectionId(@Param("id") Integer id);


}
