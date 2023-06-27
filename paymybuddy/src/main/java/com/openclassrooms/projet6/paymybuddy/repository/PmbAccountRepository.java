package com.openclassrooms.projet6.paymybuddy.repository;

import com.openclassrooms.projet6.paymybuddy.model.PmbAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PmbAccountRepository extends JpaRepository<PmbAccount, Integer> {
}
