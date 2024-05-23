package com.balashovmaksim.taco.tacocloud.controllers;

import com.balashovmaksim.taco.tacocloud.dto.UserCreateDto;
import com.balashovmaksim.taco.tacocloud.dto.UserReadDto;
import com.balashovmaksim.taco.tacocloud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/register")
    public String registerForm(){
        return "registration";
    }

    @PostMapping("/register")
    public String registrationUser(UserCreateDto userCreateDto){
        userService.save(userCreateDto);
        return "redirect:/login";
    }
    @GetMapping("/profile")
    public String profileUser(Model model, @AuthenticationPrincipal UserDetails principal){
        if (principal == null) {
            throw new RuntimeException("You are not authorized");
        }
        UserReadDto userReadDto = userService.getUserProfile(principal.getUsername());
        model.addAttribute("userReadDto", userReadDto);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute("userReadDto") UserReadDto userReadDto) {
        userService.updateProfile(userReadDto);
        return "redirect:/users/profile?success";
    }
}


