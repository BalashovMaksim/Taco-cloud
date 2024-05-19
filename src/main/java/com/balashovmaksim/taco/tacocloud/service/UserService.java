package com.balashovmaksim.taco.tacocloud.service;

import com.balashovmaksim.taco.tacocloud.dto.OrderSummaryDto;
import com.balashovmaksim.taco.tacocloud.dto.UserCreateDto;
import com.balashovmaksim.taco.tacocloud.dto.UserReadDto;
import com.balashovmaksim.taco.tacocloud.mapper.UserMapper;
import com.balashovmaksim.taco.tacocloud.model.Bucket;
import com.balashovmaksim.taco.tacocloud.model.User;
import com.balashovmaksim.taco.tacocloud.repository.BucketRepository;
import com.balashovmaksim.taco.tacocloud.repository.OrderRepository;
import com.balashovmaksim.taco.tacocloud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;
    private final BucketRepository bucketRepository;
    private final UserMapper userMapper;


    @Transactional
    public void save(UserCreateDto userCreateDto) {
        if (!Objects.equals(userCreateDto.getPassword(), userCreateDto.getConfirmPassword())) {
            throw new RuntimeException("Password is not equals");
        }

        User user = userCreateDto.toUser(passwordEncoder);
        user = userRepository.save(user);

        Bucket bucket = new Bucket();
        bucket.setUser(user);
        bucket = bucketRepository.save(bucket);

        user.setBucket(bucket);
        userRepository.save(user);
    }
    @Transactional
    public UserReadDto getUserProfile(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with name " + username));

        List<OrderSummaryDto> orderSummaries = orderRepository.findByUserId(user.getId()).stream()
                .map(order -> OrderSummaryDto.builder()
                        .id(order.getId())
                        .placedAt(order.getPlacedAt())
                        .build())
                .collect(Collectors.toList());

        return UserReadDto.builder()
                .username(user.getUsername())
                .fullname(user.getFullname())
                .street(user.getStreet())
                .city(user.getCity())
                .state(user.getState())
                .zip(user.getZip())
                .phoneNumber(user.getPhoneNumber())
                .orders(orderSummaries)
                .build();
    }

    @Transactional
    public void updateProfile(UserReadDto userReadDto) {
        User savedUser = userRepository.findFirstByUsername(userReadDto.getUsername());

        if (savedUser == null) {
            throw new RuntimeException("User not found by name " + userReadDto.getUsername());
        }

        boolean isChanged = false;

        if (userReadDto.getPassword() != null && !userReadDto.getPassword().isEmpty()) {
            savedUser.setPassword(passwordEncoder.encode(userReadDto.getPassword()));
            isChanged = true;
        }

        if (userReadDto.getFullname() != null && !userReadDto.getFullname().equals(savedUser.getFullname())) {
            savedUser.setFullname(userReadDto.getFullname());
            isChanged = true;
        }

        if (userReadDto.getStreet() != null && !userReadDto.getStreet().equals(savedUser.getStreet())) {
            savedUser.setStreet(userReadDto.getStreet());
            isChanged = true;
        }

        if (userReadDto.getCity() != null && !userReadDto.getCity().equals(savedUser.getCity())) {
            savedUser.setCity(userReadDto.getCity());
            isChanged = true;
        }

        if (userReadDto.getState() != null && !userReadDto.getState().equals(savedUser.getState())) {
            savedUser.setState(userReadDto.getState());
            isChanged = true;
        }

        if (userReadDto.getZip() != null && !userReadDto.getZip().equals(savedUser.getZip())) {
            savedUser.setZip(userReadDto.getZip());
            isChanged = true;
        }

        if (userReadDto.getPhoneNumber() != null && !userReadDto.getPhoneNumber().equals(savedUser.getPhoneNumber())) {
            savedUser.setPhoneNumber(userReadDto.getPhoneNumber());
            isChanged = true;
        }

        if (isChanged) {
            userRepository.save(savedUser);
        }
    }
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with name " + username));
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        System.out.println(user);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

}
