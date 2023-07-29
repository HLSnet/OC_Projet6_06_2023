package com.openclassrooms.projet6.paymybuddy.controller;


import com.openclassrooms.projet6.paymybuddy.dto.BuddyConnectedDto;
import com.openclassrooms.projet6.paymybuddy.dto.ProfileDto;
import com.openclassrooms.projet6.paymybuddy.dto.TransactionDto;
import com.openclassrooms.projet6.paymybuddy.service.PayMyBuddyService;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.openclassrooms.projet6.paymybuddy.constants.Constants.NON_EXISTING_ACCOUNT;

@RestController
public class PayMyBuddyController {
     private static Logger logger = LoggerFactory.getLogger(PayMyBuddyController.class);

    @Autowired
    private PayMyBuddyService payMyBuddyService;



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // This URL should return the balance of the PayMyBuddy account related to a connection
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //http://localhost:8080/balanceAccount?connectionId=<connectionId>
    @GetMapping("/balanceAccount")
    public ResponseEntity<Float> getBalanceAccountGivenConnectionId(@RequestParam int connectionId, @NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {}?connectionId={}", request.getMethod(), request.getRequestURL(), connectionId);

        float balanceAccount = payMyBuddyService.getBalanceAccount(connectionId);
        if (balanceAccount == NON_EXISTING_ACCOUNT) {
            logger.error(" Resultat de la requete {} en cours : statut = 204 No Content", request.getMethod());
            return ResponseEntity.noContent().build();
        }
        logger.info(" Resultat de la requete {} en cours : statut =  200 OK ; reponse = {}", request.getMethod(), balanceAccount);
        return ResponseEntity.ok(balanceAccount);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // This URL should return the list of transactions  related to a connection
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //http://localhost:8080/transactions?connectionId=<connectionId>
    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionDto>> getTransactionsGivenConnectionId(@RequestParam int connectionId, @NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {}?connectionId={}", request.getMethod(), request.getRequestURL(), connectionId);

        List<TransactionDto> transactionDtos = payMyBuddyService.getTransactions(connectionId);


        if (transactionDtos.isEmpty()) {
            logger.error(" Resultat de la requete {} en cours : statut = 204 No Content", request.getMethod());
            return ResponseEntity.noContent().build();
        }
        logger.info(" Resultat de la requete {} en cours : statut =  200 OK ; reponse = {}", request.getMethod(), transactionDtos);



        return ResponseEntity.ok(transactionDtos);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // This URL should return the list of contacts (my buddies)  related to a connection
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // http://localhost:8080/buddies?connectionId=<connectionId>
    @GetMapping("/buddies")
    public ResponseEntity<List<BuddyConnectedDto>> getBuddiesConnectedGivenConnectionId(@RequestParam int connectionId, @NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {}?connectionId={}", request.getMethod(), request.getRequestURL(), connectionId);

        List<BuddyConnectedDto> BuddyConnectedDtos = payMyBuddyService.getBuddiesConnected(connectionId);

        return ResponseEntity.ok(BuddyConnectedDtos);
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // This URL should add a contact to the list of contacts  related to a connection
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // http://localhost:8080/addBuddies?connectionId=<connectionId>&connectionBuddyId=<connectionBuddyId>





    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // This URL should add a transaction related to a connection (money sender)
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // http://localhost:8080/addTransaction?connectionId=<connectionId>






    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // This URL should return the PMB contact (in order to contact the PayMyBuddy company)
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // http://localhost:8080/contact
    @GetMapping("/contact")
    public ResponseEntity<String> getContact(@NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {}?connectionId={}", request.getMethod(), request.getRequestURL());

        String contact = payMyBuddyService.getContact();

        return ResponseEntity.ok(contact);
    }




    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // This URL should return one's connection's profile
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // http://localhost:8080/profile?connectionId=<connectionId>
    @GetMapping("/profile")
    public ResponseEntity<ProfileDto> getProfileGivenConnectionId(@RequestParam int connectionId, @NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {}?connectionId={}", request.getMethod(), request.getRequestURL(), connectionId);

        ProfileDto profileDto = payMyBuddyService.getProfile(connectionId);

        return ResponseEntity.ok(profileDto);
    }






    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // This URL should update one's connection's profile
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //http://localhost:8080/updateProfile?connectionId=<connectionId>
//    @PostMapping(value = "/profile")
//    public ResponseEntity<Void> addPerson(@RequestBody ProfileDto profileDto, @NotNull HttpServletRequest request) {
//        logger.info(" Requete {} en cours : {} ressource a ajouter {}", request.getMethod(), request.getRequestURL(), profileDto);
//
//        if (!personDao.save(person)) {
//            logger.error(" Resultat de la requete {} en cours : statut = 409 conflit la ressource existe deja", request.getMethod());
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
//        // On renvoie le code "201 Created" et l'URI vers la ressource créée dans le champ Location.
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{firstName}/{lastName}")
//                .buildAndExpand(person.getFirstName() ,person.getLastName())
//                .toUri();
//
//        logger.info(" Resultat de la requete {} en cours : statut =  201 Created ; URL = {}", request.getMethod(), location);
//        return ResponseEntity.created(location).build();
//
//    }




    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // This URL should Register, create an account
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // http://localhost:8080/register





    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // This URL should Log in
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // http://localhost:8080/login





    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // This URL should Log out
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // http://localhost:8080/logout

}
