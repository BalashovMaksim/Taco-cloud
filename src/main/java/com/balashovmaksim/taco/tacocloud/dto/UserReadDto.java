package com.balashovmaksim.taco.tacocloud.dto;

import com.balashovmaksim.taco.tacocloud.model.Role;
import com.balashovmaksim.taco.tacocloud.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserReadDto {
    private String username;
    private String password;
    private String confirmPassword;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, passwordEncoder.encode(password), fullname, street, city, state, zip, phoneNumber, Role.USER);
    }
}
