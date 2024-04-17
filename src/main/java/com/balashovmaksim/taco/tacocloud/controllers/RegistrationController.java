package com.balashovmaksim.taco.tacocloud.controllers;

import com.balashovmaksim.taco.tacocloud.model.RegistrationForm;
import com.balashovmaksim.taco.tacocloud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String registerForm(){
        return "registration";
    }
    @PostMapping
    public String processRegistration(RegistrationForm registrationForm){
        if(!Objects.equals(registrationForm.getPassword(), registrationForm.getConfirmPassword())){
            throw new RuntimeException("Password is not equals");
        }
        userRepository.save(registrationForm.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
