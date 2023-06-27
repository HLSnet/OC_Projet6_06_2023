package com.openclassrooms.projet6.paymybuddy.servicetest;

import com.openclassrooms.projet6.paymybuddy.dto.ConnectionDto;
import com.openclassrooms.projet6.paymybuddy.service.PayMyBuddyService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Sql(scripts = "classpath:creation_alimentation_BDD_test.sql")
@Transactional
public class PayMyBuddyServiceTest {
    @Autowired
    PayMyBuddyService payMyBuddyServiceImpl;

    @Test
    void testGetBuddiesConnectionWithExistingId() {
        // ARRANGE
        int identifiant = 2;

        // ACT
        List<ConnectionDto> buddiesConnected = payMyBuddyServiceImpl.getBuddiesConnection(identifiant);

        // ASSERT
        assertTrue(buddiesConnected.size() == 3);
        assertTrue(buddiesConnected.get(0).getEmail().equals("user3_test@gmail.com"));
        assertTrue(buddiesConnected.get(1).getEmail().equals("user4_test@gmail.com"));
        assertTrue(buddiesConnected.get(2).getEmail().equals("user5_test@gmail.com"));
    }

    @Test
    void testGetBuddiesConnectionWithNonExistingId() {
        // ARRANGE
        int identifiant = 100;

        // ACT, ASSERT
        assertTrue(payMyBuddyServiceImpl.getBuddiesConnection(identifiant).isEmpty());
    }
}
