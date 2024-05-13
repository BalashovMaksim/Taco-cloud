package com.balashovmaksim.taco.tacocloud.service;

import com.balashovmaksim.taco.tacocloud.dto.UserCreateDto;
import com.balashovmaksim.taco.tacocloud.dto.UserReadDto;
import com.balashovmaksim.taco.tacocloud.mapper.UserMapper;
import com.balashovmaksim.taco.tacocloud.model.User;
import com.balashovmaksim.taco.tacocloud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    @Transactional
    public void save(UserCreateDto userCreateDto){
        if(!Objects.equals(userCreateDto.getPassword(), userCreateDto.getConfirmPassword())){
            throw new RuntimeException("Password is not equals");
        }
        userRepository.save(userCreateDto.toUser(passwordEncoder));
    }
    @Transactional
    public UserReadDto getUserProfile(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return userMapper.toDto(user);
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found with name " + username);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        System.out.println(user);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
