package com.openclassrooms.projet6.paymybuddy.controller;


import com.openclassrooms.projet6.paymybuddy.service.PayMyBuddyService;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private PayMyBuddyService payMyBuddyService;

    // http://localhost:8080/login
    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }


    // http://localhost:8080/registration
    @GetMapping("/registration")
    public String registrationForm() {
        return "registration";
    }


    // http://localhost:8080/registration
    @PostMapping("/registration")
    public String registration(@NotNull HttpServletRequest request,
                               @RequestParam("username") String email,
                               @RequestParam("name") String name,
                               @RequestParam("password") String password) {

        logger.info(" Requete {} en cours : {}", request.getMethod(), request.getRequestURL());

        return  (payMyBuddyService.registration(email, name, password))? "redirect:/registration?success" : "redirect:/registration?error";

    }
}
