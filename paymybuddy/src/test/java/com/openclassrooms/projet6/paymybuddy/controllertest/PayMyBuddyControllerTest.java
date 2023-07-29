package com.openclassrooms.projet6.paymybuddy.controllertest;


import com.openclassrooms.projet6.paymybuddy.controller.PayMyBuddyController;
import com.openclassrooms.projet6.paymybuddy.dto.BuddyConnectedDto;
import com.openclassrooms.projet6.paymybuddy.dto.ProfileDto;
import com.openclassrooms.projet6.paymybuddy.dto.TransactionDto;
import com.openclassrooms.projet6.paymybuddy.service.PayMyBuddyService;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;

import static com.openclassrooms.projet6.paymybuddy.constants.Constants.NON_EXISTING_ACCOUNT;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PayMyBuddyController.class)
@AutoConfigureMockMvc(addFilters = false) // Désactive les filtres de sécurité
public class PayMyBuddyControllerTest {
    private static Logger logger = LoggerFactory.getLogger(PayMyBuddyControllerTest.class);

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PayMyBuddyService payMyBuddyService;


    //***************************************************************************************************
    //***************************************************************************************************
    // Tests unitaires de la classe  PayMyBuddyController
    //***************************************************************************************************
    //***************************************************************************************************

    //***************************************************************************************************
    // http://localhost:8080/balanceAccount?connectionId=<connectionId>
    //***************************************************************************************************
    @Test
    public void shouldReturnTheBalanceOfPayMyBuddyAccountRelatedToAConnection_ResultBalanceAccount() throws Exception{
        int connectionId = 2;
        float balance = 200;

        when(payMyBuddyService.getBalanceAccount(connectionId)).thenReturn(balance);
        logger.info("TU -> shouldReturnTheBalanceOfPayMyBuddyAccountRelatedToAConnection_ResultNotNull() : Test unitaire de cas nominal de la methode payMyBuddyService::getBalanceAccount");

        mockMvc.perform(get("http://localhost:8080/balanceAccount?connectionId="+connectionId)).andExpect(status().isOk());

        verify(payMyBuddyService,times(1)).getBalanceAccount(connectionId);
    }
    @Test
    public void shouldReturnTheBalanceOfPayMyBuddyAccountRelatedToAConnection_ResultNonExistingAccount() throws Exception{
        int connectionId = 100;
        float balance = NON_EXISTING_ACCOUNT;

        when(payMyBuddyService.getBalanceAccount(connectionId)).thenReturn(balance);
        logger.info("TU -> shouldReturnTheBalanceOfPayMyBuddyAccountRelatedToAConnection_ResultNull() : Test unitaire de cas d'erreur de la methode payMyBuddyService::getBalanceAccount");

        mockMvc.perform(get("http://localhost:8080/balanceAccount?connectionId="+connectionId)).andExpect(status().isNoContent());

        verify(payMyBuddyService,times(1)).getBalanceAccount(connectionId);
    }


    //***************************************************************************************************
    // http://localhost:8080/transactions?connectionId=<connectionId>
    //***************************************************************************************************
    @Test
    public void shouldReturnTheListOfTransactionsRelatedToAConnection_ResultListNotEmpty() throws Exception{
        int connectionId = 2;
        String name = "Mr X";
        String description = "Transaction PMB_account2 vers PMB_accountX";
        float amount = 1000;

        List<TransactionDto> transactionDtos = new ArrayList<>();
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setConnectionReceiverId(connectionId);
        transactionDto.setName(name);
        transactionDto.setDescription(description);
        transactionDto.setAmount(amount);
        transactionDtos.add(transactionDto);

        when(payMyBuddyService.getTransactions(connectionId)).thenReturn(transactionDtos);
        logger.info("TU -> shouldReturnTheListOfTransactionsRelatedToAConnection_ResultListNotEmpty() : Test unitaire de cas nominal de la methode payMyBuddyService::getTransactions");

        mockMvc.perform(get("http://localhost:8080/transactions?connectionId="+connectionId)).andExpect(status().isOk());

        verify(payMyBuddyService,times(1)).getTransactions(connectionId);
    }

    @Test
    public void shouldReturnTheListOfTransactionsRelatedToAConnection_ResultListEmpty() throws Exception{
        int connectionId = 2;
        List<TransactionDto> transactionDtos = new ArrayList<>();

        when(payMyBuddyService.getTransactions(connectionId)).thenReturn(transactionDtos);
        logger.info("TU -> shouldReturnTheListOfTransactionsRelatedToAConnection_ResultListEmpty() : Test unitaire de cas d'erreur de la methode payMyBuddyService::getTransactions");

        mockMvc.perform(get("http://localhost:8080/transactions?connectionId="+connectionId)).andExpect(status().isNoContent());

        verify(payMyBuddyService,times(1)).getTransactions(connectionId);
    }

    //***************************************************************************************************
    // http://localhost:8080/buddies?=<connectionId>
    //***************************************************************************************************

    //***************************************************************************************************
    // http://localhost:8080/addBuddies?connectionId=<connectionId>&connectionBuddyId=<connectionBuddyId>
    //***************************************************************************************************

    //***************************************************************************************************
    // http://localhost:8080/addTransaction?connectionId=<connectionId>
    //***************************************************************************************************

    //***************************************************************************************************
    // http://localhost:8080/contact
    //***************************************************************************************************

    //***************************************************************************************************
    // http://localhost:8080/profile?connectionId=<connectionId>
    //***************************************************************************************************

    //***************************************************************************************************
    // http://localhost:8080/updateProfile?connectionId=<connectionId>
    //***************************************************************************************************

    //***************************************************************************************************
    // http://localhost:8080/register
    //***************************************************************************************************

    //***************************************************************************************************
    // http://localhost:8080/login
    //***************************************************************************************************

    //***************************************************************************************************
    // http://localhost:8080/logout
    //***************************************************************************************************





}
