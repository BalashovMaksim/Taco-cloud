package com.balashovmaksim.taco.tacocloud.controllers;

import com.balashovmaksim.taco.tacocloud.model.User;
import com.balashovmaksim.taco.tacocloud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
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
}

