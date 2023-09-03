package com.openclassrooms.projet6.paymybuddy.controller;


import com.openclassrooms.projet6.paymybuddy.dto.*;
import com.openclassrooms.projet6.paymybuddy.security.CustomUserDetails;
import com.openclassrooms.projet6.paymybuddy.service.PayMyBuddyService;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.openclassrooms.projet6.paymybuddy.constants.Constants.COORDONNEES_CONTACT;

@Controller
public class PayMyBuddyController {
     private static Logger logger = LoggerFactory.getLogger(PayMyBuddyController.class);

    @Autowired
    private PayMyBuddyService payMyBuddyService;

    @GetMapping("/")
    public String greeting() {
        return "redirect:/home";
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // This URL should return the home page once one is authentified
    //
    // the home page contains the balance information of one's PayMyBuddy account
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // http://localhost:8080/home
    @GetMapping("/home")
    public String home(@NotNull HttpServletRequest request, Model model) {
        logger.info(" Requete {} en cours : {}", request.getMethod(), request.getRequestURL());

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        HomeDto homeDto = payMyBuddyService.getBalanceAccount(customUserDetails.getConnectionId());

        model.addAttribute("balanceAccount", homeDto.getBalance());
        model.addAttribute("name", homeDto.getName());
        model.addAttribute("amount", "0");

        return "home";
    }

    // http://localhost:8080/fromMyBank
    @PostMapping("/fromMyBank")
    public String fromMyBank(@NotNull HttpServletRequest request, @RequestParam("amount") float amount) {
        logger.info(" Requete {} en cours : {}", request.getMethod(), request.getRequestURL());

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        payMyBuddyService.addToBalance(customUserDetails.getConnectionId(), amount);

        return "redirect:/home";
    }

    // http://localhost:8080/toMyBank
    @PostMapping("/toMyBank")
    public String toMyBank(@NotNull HttpServletRequest request, @RequestParam("amount") float amount) {
        logger.info(" Requete {} en cours : {}", request.getMethod(), request.getRequestURL());

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean result = payMyBuddyService.addToBalance(customUserDetails.getConnectionId(), -1*amount);
        return  (result)?  "redirect:/home" : "discardTransferBank";
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // This URL should return the page dealing with transfer
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //http://localhost:8080/transfer
    @GetMapping("/transfer")
    public String transfer(@NotNull HttpServletRequest request, Model model,
    @RequestParam(value = "page", defaultValue = "0") int page) {

        logger.info(" Requete {} en cours : {}", request.getMethod(), request.getRequestURL());

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        TransferDto transferDto = payMyBuddyService.getTransferPageInformations(customUserDetails.getConnectionId());

        int pageSize = 3;
        int totalPages = (int) Math.ceil((double) transferDto.getTransactionDtos().size() / pageSize);

        int start = page * pageSize;
        int end = Math.min(start + pageSize, transferDto.getTransactionDtos().size());

        List<TransactionDto> transactionsOnPage = transferDto.getTransactionDtos().subList(start, end);

        model.addAttribute("transactionDtos", transactionsOnPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("buddyConnectedDtos", transferDto.getBuddyConnectedDtos());
        model.addAttribute("transaction", new TransactionDto());

        return "transfer";
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // This URL
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //http://localhost:8080/addTransaction
    @PostMapping("/addTransaction")
    public String addTransaction(@NotNull HttpServletRequest request, @ModelAttribute TransactionDto transactionDto) {

        logger.info(" Requete {} en cours : {}", request.getMethod(), request.getRequestURL());

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean result = payMyBuddyService.addTransaction(customUserDetails.getConnectionId(), transactionDto);

        return  (result)?  "redirect:/transfer" : "discardTransferBuddy";
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // This URL
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/addConnection")
    public String addConnection(@NotNull HttpServletRequest request) {

        logger.info(" Requete {} en cours : {}", request.getMethod(), request.getRequestURL());

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        return  "addConnection";
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // This URL
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //http://localhost:8080/addConnection
    @PostMapping("/addBuddy")
    public String addConnection(@NotNull HttpServletRequest request,  @RequestParam("email")  String email) {

        logger.info(" Requete {} en cours : {}", request.getMethod(), request.getRequestURL());

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean resultat = payMyBuddyService.addBuddyConnected(customUserDetails.getConnectionId(), email);

        if (resultat) { System.out.println("Email ajouté : " + email);}
        else {System.out.println("Email inconnu : " + email);}

        return  "redirect:/transfer";
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // This URL should return one's connection's profile
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //http://localhost:8080/profile
    @GetMapping("/profile")
    public String profile(@NotNull HttpServletRequest request, Model model) {
        logger.info(" Requete {} en cours : {}", request.getMethod(), request.getRequestURL());

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ProfileDto profileDto = payMyBuddyService.getProfile(customUserDetails.getConnectionId());

        String name = profileDto.getName();
        String email = profileDto.getEmail();
        String password = "******";

        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("password", password);

        return "profile";
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // This URL should return the page of the PMB contact (in order to contact the PayMyBuddy company)
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //http://localhost:8080/contact
    @GetMapping("/contact")
    public String contact(@NotNull HttpServletRequest request, Model model) {
        logger.info(" Requete {} en cours : {}", request.getMethod(), request.getRequestURL());

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("contact", COORDONNEES_CONTACT);
        return "contact";
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // This URL should update one's connection's profile   <= A SUPPRIMER , APRES AVOIR ECRIT LES TEST CONTROLEUR (ANNCIENNE VERSION, CADUQUE)
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // This URL should add a transaction related to a connection (money sender)  <= A SUPPRIMER , APRES AVOIR ECRIT LES TEST CONTROLEUR (ANNCIENNE VERSION, CADUQUE)
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    // http://localhost:8080/addTransaction?connectionBuddyId=<connectionBuddyId>
//    @GetMapping("/addTransaction")
//    public ResponseEntity<Void> addTransactionGivenConnectionId(@RequestParam int connectionBuddyId, @RequestBody TransactionDto transactionDto, @NotNull HttpServletRequest request) {
//        logger.info(" Requete {} en cours : {}?connectionId={}", request.getMethod(), request.getRequestURL(), connectionBuddyId);
//
//        boolean result = payMyBuddyService.addTransaction(connectionBuddyId, transactionDto);
//
//        if (!result) {
//            logger.error(" Resultat de la requete {} en cours : statut = 409 conflit la ressource existe deja", request.getMethod());
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
//
//        logger.error(" Resultat de la requete {} en cours : statut = 409 conflit la ressource existe deja", request.getMethod());
//
//
//
//        logger.info(" Resultat de la requete {} en cours : statut =  200 OK ; reponse = {}", request.getMethod(), result);
//
//
//        return ResponseEntity.created(location).build();
//    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // This URL should add a contact (a buddy) to the list of contacts (buddies) related to a connection <= A SUPPRIMER , APRES AVOIR ECRIT LES TEST CONTROLEUR (ANNCIENNE VERSION, CADUQUE)
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    // http://localhost:8080/addBuddy?connectionId=<connectionId>&connectionBuddyId=<connectionBuddyId>
//    @GetMapping("/addBuddy")
//    public ResponseEntity<Void> addBuddiesConnectedGivenConnectionId(@RequestParam int connectionId, @RequestParam int connectionBuddyId, @NotNull HttpServletRequest request) {
//        logger.info(" Requete {} en cours : {}?connectionId={}", request.getMethod(), request.getRequestURL(), connectionId);
//
//        boolean result = payMyBuddyService.addBuddyConnected(connectionId, connectionBuddyId);
//
//        if (!result) {
//            logger.error(" Resultat de la requete {} en cours : statut = 409 conflit : connection(s) non existantes", request.getMethod());
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
//
//        logger.info(" Resultat de la requete {} en cours : statut =  200 OK ; reponse = {}", request.getMethod(), result);
//        return ResponseEntity.ok(null);
//    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // This URL should return the list of contacts (my buddies)  related to a connection  <= A SUPPRIMER , APRES AVOIR ECRIT LES TEST CONTROLEUR (ANNCIENNE VERSION, CADUQUE)
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // http://localhost:8080/buddies?connectionId=<connectionId>
//    @GetMapping("/buddies")
//    public ResponseEntity<List<BuddyConnectedDto>> getBuddiesConnectedGivenConnectionId(@RequestParam int connectionId, @NotNull HttpServletRequest request) {
//        logger.info(" Requete {} en cours : {}?connectionId={}", request.getMethod(), request.getRequestURL(), connectionId);
//
//        List<BuddyConnectedDto> BuddyConnectedDtos = payMyBuddyService.getBuddiesConnected(connectionId);
//
//        if (BuddyConnectedDtos.isEmpty()) {
//            logger.error(" Resultat de la requete {} en cours : statut = 204 No Content", request.getMethod());
//            return ResponseEntity.noContent().build();
//        }
//        logger.info(" Resultat de la requete {} en cours : statut =  200 OK ", request.getMethod(), BuddyConnectedDtos);
//
//
//        return ResponseEntity.ok(BuddyConnectedDtos);
//    }


}
