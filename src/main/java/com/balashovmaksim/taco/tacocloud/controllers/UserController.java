package com.balashovmaksim.taco.tacocloud.controllers;

import com.balashovmaksim.taco.tacocloud.dto.UserCreateDto;
import com.balashovmaksim.taco.tacocloud.dto.UserReadDto;
import com.balashovmaksim.taco.tacocloud.dto.UserUpdateDto;
import com.balashovmaksim.taco.tacocloud.model.User;
import com.balashovmaksim.taco.tacocloud.repository.UserRepository;
import com.balashovmaksim.taco.tacocloud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{username}/roles")
    @ResponseBody
    public String getRoles(@PathVariable("username") String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user.getRole().name();
        } else {
            throw new UsernameNotFoundException("User " + username + " not found");
        }
    }
    @GetMapping("/info")
    @ResponseBody
    public String userInfo(Authentication authentication) {
        if (authentication != null) {
            String username = authentication.getName();
            String roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(", "));
            String info = "Username: " + username + ", Roles: " + roles;
            return info;
        }
        return "No authenticated user";
    }

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
    public String profileUser(Model model, Principal principal){
        if(principal == null){
            throw new RuntimeException("You are not authorized");
        }
        UserReadDto userReadDto = userService.getUserProfile(principal.getName());
        model.addAttribute("userReadDto", userReadDto);
        return "profile";
    }

}

