package com.balashovmaksim.taco.tacocloud.configuration;

import com.balashovmaksim.taco.tacocloud.model.RegistrationForm;
import com.balashovmaksim.taco.tacocloud.model.User;
import com.balashovmaksim.taco.tacocloud.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return username -> {
            User user = userRepository.findByUsername(username);
            if (user != null){
                return user;
            }
            throw new UsernameNotFoundException("User " + username + " not found");
        };
    }

    @Bean
    @Primary
    public SecurityFilterChain tacoSecurityConfig(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/orders", "/design").hasRole("USER")
                        .requestMatchers("/**").permitAll())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/"))
                .build();
    }
}
