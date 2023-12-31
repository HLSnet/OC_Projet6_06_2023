package com.openclassrooms.projet6.paymybuddy.controllertest;


import com.openclassrooms.projet6.paymybuddy.controller.PayMyBuddyController;
import com.openclassrooms.projet6.paymybuddy.dto.*;
import com.openclassrooms.projet6.paymybuddy.security.CustomUserDetails;
import com.openclassrooms.projet6.paymybuddy.service.CustomUserDetailsService;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;
import java.util.List;

import static com.openclassrooms.projet6.paymybuddy.constants.Constants.COORDONNEES_CONTACT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PayMyBuddyController.class)
//@AutoConfigureMockMvc(addFilters = false) // Désactive les filtres de sécurité
public class PayMyBuddyControllerTest {
    private static Logger logger = LoggerFactory.getLogger(PayMyBuddyControllerTest.class);

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PayMyBuddyService payMyBuddyService;

    @MockBean
    CustomUserDetails customUserDetails;

    @MockBean
    CustomUserDetailsService customUserDetailsService;


    //***************************************************************************************************
    //***************************************************************************************************
    // Tests unitaires de la classe  PayMyBuddyController
    //***************************************************************************************************
    //***************************************************************************************************

    //***************************************************************************************************
    // 1/ http://localhost:8080/home
    //***************************************************************************************************
    //@WithMockUser(value = "connection2_test@gmail.com")
//    @WithUserDetails(value="connection2_test@gmail.com", userDetailsServiceBeanName="customUserDetailsService")
// //   @WithMockUser(value = "connection2@gmail.com", userDetailsServiceBeanName = "customUserDetailsService")
//    @Test
//    public void shouldReturnTheBalanceOfPayMyBuddyAccountRelatedToAConnection_ResultBalanceAccount() throws Exception{
//        // Mock a springSecurity user
//        CustomUserDetails customUserDetails = new CustomUserDetails(2,
//                                                                    "connection2@gmail.com",
//                                                                    "pwd2",
//                                                                    new ArrayList<>());
//
//        // Mock a HomeDto object
//        float balance = 200;
//        String name = "buddy2";
//        HomeDto homeDto = new HomeDto();
//        homeDto.setName(name);
//        homeDto.setBalance(balance);
//
//        // Configure the behavior of the customUserDetailsService mock
//        when(customUserDetailsService.loadUserByUsername("connection2@gmail.com")).thenReturn(customUserDetails);
//
//        // Configure the behavior of the payMyBuddyService mock
//        when(payMyBuddyService.getBalanceAccount(customUserDetails.getConnectionId())).thenReturn(homeDto);
//
//        mockMvc.perform(get("/home")).andExpect(status().isOk());
//
//        //verify(payMyBuddyService,times(1)).getBalanceAccount(customUserDetails.getConnectionId());
//    }
//

    //***************************************************************************************************
    // 2/ http://localhost:8080/fromMyBank
    //***************************************************************************************************


    //***************************************************************************************************
    // 3/ http://localhost:8080/toMyBank
    //***************************************************************************************************



    //***************************************************************************************************
    // 4/ http://localhost:8080/transfer
    //***************************************************************************************************


    //***************************************************************************************************
    // 5/ http://localhost:8080/addTransaction
    //***************************************************************************************************


    //***************************************************************************************************
    // 6/ http://localhost:8080/addConnection
    //***************************************************************************************************
    @WithMockUser(value = "connection2_test@gmail.com")
    @Test
    public void shouldReturnAddConnection() throws Exception{
        mockMvc.perform(get("http://localhost:8080/addConnection")).
                andExpect(status().isOk()).
                andExpect(view().name("addConnection"));
    }


    //***************************************************************************************************
    // 7/ http://localhost:8080/addBuddy
    //***************************************************************************************************



    //***************************************************************************************************
    // 8/ http://localhost:8080/profile
    //***************************************************************************************************



    //***************************************************************************************************
    // 9/ http://localhost:8080/contact
    //***************************************************************************************************
    @WithMockUser(value = "connection2_test@gmail.com")
    @Test
    public void shouldReturnContact() throws Exception{
        mockMvc.perform(get("http://localhost:8080/contact")).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("contact")).
                andExpect(model().attribute("contact", COORDONNEES_CONTACT)).
                andExpect(view().name("contact"));
    }






    //***************************************************************************************************
    // 1/ http://localhost:8080/balanceAccount?connectionId=<connectionId>
    //***************************************************************************************************
//    @Test
//    public void shouldReturnTheBalanceOfPayMyBuddyAccountRelatedToAConnection_ResultBalanceAccount() throws Exception{
//        int connectionId = 2;
//        float balance = 200;
//        String name = "buddy2";
//
//        BalanceDto balanceDto = new BalanceDto();
//        balanceDto.setName(name);
//        balanceDto.setBalance(balance);
//
//        when(payMyBuddyService.getBalanceAccount(connectionId)).thenReturn(balanceDto);
//        logger.info("TU (cas nominal) -> shouldReturnTheBalanceOfPayMyBuddyAccountRelatedToAConnection_ResultNotNull()");
//
//        mockMvc.perform(get("http://localhost:8080/balanceAccount?connectionId="+connectionId)).andExpect(status().isOk());
//
//        verify(payMyBuddyService,times(1)).getBalanceAccount(connectionId);
//    }
//    @Test
//    public void shouldReturnTheBalanceOfPayMyBuddyAccountRelatedToAConnection_ResultNonExistingAccount() throws Exception{
//        int connectionId = 100;
//        float balance = NON_EXISTING_ACCOUNT;
//
//        when(payMyBuddyService.getBalanceAccount(connectionId)).thenReturn(balance);
//        logger.info("TU (cas d'erreur) -> shouldReturnTheBalanceOfPayMyBuddyAccountRelatedToAConnection_ResultNull()");
//
//        mockMvc.perform(get("http://localhost:8080/balanceAccount?connectionId="+connectionId)).andExpect(status().isNoContent());
//
//        verify(payMyBuddyService,times(1)).getBalanceAccount(connectionId);
//    }


//    //***************************************************************************************************
//    // 2/ http://localhost:8080/transactions?connectionId=<connectionId>
//    //***************************************************************************************************
//    @Test
//    public void shouldReturnTheListOfTransactionsRelatedToAConnection_ResultListNotEmpty() throws Exception{
//        int connectionId = 2;
//        String name = "My buddy";
//        String description = "Transaction PMB_account2 vers PMB_accountMy buddy";
//        float amount = 1000;
//
//        List<TransactionDto> transactionDtos = new ArrayList<>();
//        TransactionDto transactionDto = new TransactionDto();
//        transactionDto.setConnectionReceiverId(connectionId);
//        transactionDto.setName(name);
//        transactionDto.setDescription(description);
//        transactionDto.setAmount(amount);
//        transactionDtos.add(transactionDto);
//
//        when(payMyBuddyService.getTransactions(connectionId)).thenReturn(transactionDtos);
//        logger.info("TU (cas nominal) -> shouldReturnTheListOfTransactionsRelatedToAConnection_ResultListNotEmpty()");
//
//        mockMvc.perform(get("http://localhost:8080/transactions?connectionId="+connectionId)).andExpect(status().isOk());
//
//        verify(payMyBuddyService,times(1)).getTransactions(connectionId);
//    }
//
//    @Test
//    public void shouldReturnTheListOfTransactionsRelatedToAConnection_ResultListEmpty() throws Exception{
//        int connectionId = 2;
//        List<TransactionDto> transactionDtos = new ArrayList<>();
//
//        when(payMyBuddyService.getTransactions(connectionId)).thenReturn(transactionDtos);
//        logger.info("TU (cas d'erreur) -> shouldReturnTheListOfTransactionsRelatedToAConnection_ResultListEmpty()");
//
//        mockMvc.perform(get("http://localhost:8080/transactions?connectionId="+connectionId)).andExpect(status().isNoContent());
//
//        verify(payMyBuddyService,times(1)).getTransactions(connectionId);
//    }
//
//    //***************************************************************************************************
//    // 3/ http://localhost:8080/buddies?=<connectionId>
//    //***************************************************************************************************
//    @Test
//    public void shouldReturnTheListOfbuddiesRelatedToAConnection_ResultListNotEmpty() throws Exception{
//        int connectionId = 2;
//        List<String> buddiesName = List.of("My buddy1", "My buddy2", "My buddy3");
//        List<BuddyConnectedDto> buddyConnectedDtos = new ArrayList<>();
//        BuddyConnectedDto buddyConnectedDto;
//        int i = 1;
//        for (String name : buddiesName){
//            buddyConnectedDto = new BuddyConnectedDto();
//            buddyConnectedDto.setConnectionId(i);
//            buddyConnectedDto.setName(name);
//            buddyConnectedDtos.add(buddyConnectedDto);
//            i++;
//        }
//
//        when(payMyBuddyService.getBuddiesConnected(connectionId)).thenReturn(buddyConnectedDtos);
//        logger.info("TU (cas nominal) -> shouldReturnTheListOfbuddiesRelatedToAConnection_ResultListEmpty()");
//
//        mockMvc.perform(get("http://localhost:8080/buddies?connectionId="+connectionId)).andExpect(status().isOk());
//
//        verify(payMyBuddyService,times(1)).getBuddiesConnected(connectionId);
//    }
//
//    @Test
//    public void shouldReturnTheListOfbuddiesRelatedToAConnection_ResultListEmpty() throws Exception{
//        int connectionId = 2;
//        List<BuddyConnectedDto> buddyConnectedDtos = new ArrayList<>();
//
//        when(payMyBuddyService.getBuddiesConnected(connectionId)).thenReturn(buddyConnectedDtos);
//        logger.info("TU (cas d'erreur) -> shouldReturnTheListOfbuddiesRelatedToAConnection_ResultListNotEmpty()");
//
//        mockMvc.perform(get("http://localhost:8080/buddies?connectionId="+connectionId)).andExpect(status().isNoContent());
//
//        verify(payMyBuddyService,times(1)).getBuddiesConnected(connectionId);
//    }
//
//

    //***************************************************************************************************
    // 4/ http://localhost:8080/addBuddy?connectionId=<connectionId>&connectionBuddyId=<connectionBuddyId>
    //***************************************************************************************************
//    @Test
//    public void shouldAddANewBuddyToTheListOfBuddiesConnected_ResultTrue() throws Exception{
//        int connectionId = 2;
//        int connectionBuddyId = 7;
//
//        when(payMyBuddyService.addBuddyConnected(connectionId, connectionBuddyId)).thenReturn(true);
//        logger.info("TU (cas nominal) -> shouldAddANewBuddyToTheListOfBuddiesConnected_ResultTrue()");
//
//        mockMvc.perform(get("http://localhost:8080/addBuddy?connectionId="+connectionId+"&connectionBuddyId="+connectionBuddyId)).andExpect(status().isOk());
//
//        verify(payMyBuddyService,times(1)).addBuddyConnected(connectionId, connectionBuddyId);
//    }
//
//    @Test
//    public void shouldAddANewBuddyToTheListOfBuddiesConnected_ResultFalse() throws Exception{
//        int connectionId = 2;
//        int connectionBuddyId = 70;
//
//        when(payMyBuddyService.addBuddyConnected(connectionId, connectionBuddyId)).thenReturn(false);
//        logger.info("TU (cas d'erreur) -> shouldAddANewBuddyToTheListOfBuddiesConnected_ResultTrue()");
//
//        mockMvc.perform(get("http://localhost:8080/addBuddy?connectionId="+connectionId+"&connectionBuddyId="+connectionBuddyId)).andExpect(status().isConflict());
//
//        verify(payMyBuddyService,times(1)).addBuddyConnected(connectionId, connectionBuddyId);
//    }
    //***************************************************************************************************
    // 5/ http://localhost:8080/addTransaction?connectionId=<connectionId>
    //***************************************************************************************************



//    //***************************************************************************************************
    // 7/ http://localhost:8080/profile?connectionId=<connectionId>
    //***************************************************************************************************
//    @Test
//    public void shouldReturnTheProfileRelatedToAConnection_ResultNotNull() throws Exception{
//        int connectionId = 2;
//        String email= "connection2_test@gmail.com";
//        String password= "pwd2";
//        String name= "buddy2";
//        ProfileDto profileDto = new ProfileDto();
//        profileDto.setConnectionId(connectionId);
//        profileDto.setEmail(email);
//        profileDto.setPassword(password);
//        profileDto.setName(name);
//
//        when(payMyBuddyService.getProfile(connectionId)).thenReturn(profileDto);
//        logger.info("TU (cas nominal) -> shouldReturnTheProfileRelatedToAConnection_ResultNotNull()");
//
//        mockMvc.perform(get("http://localhost:8080/profile?connectionId="+connectionId)).andExpect(status().isOk());
//
//        verify(payMyBuddyService,times(1)).getProfile(connectionId);
//    }


}
