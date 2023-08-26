package com.openclassrooms.projet6.paymybuddy.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);



    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }



    @GetMapping("/registration")
    public String registrationForm() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(){
        return "redirect:/registration?success";
    }


//    public String registration(
//            @Valid @ModelAttribute("user") UserDto userDto,
//            BindingResult result,
//            Model model) {
//        User existingUser = userService.findUserByEmail(userDto.getEmail());
//
//        if (existingUser != null)
//            result.rejectValue("email", null,
//                    "User already registered !!!");
//
//        if (result.hasErrors()) {
//            model.addAttribute("user", userDto);
//            return "/registration";
//        }
//
//        userService.saveUser(userDto);
//        return "redirect:/registration?success";
//    }
}
