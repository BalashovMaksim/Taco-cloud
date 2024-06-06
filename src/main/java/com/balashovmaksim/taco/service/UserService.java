package com.balashovmaksim.taco.service;

import com.balashovmaksim.taco.dto.OrderSummaryDto;
import com.balashovmaksim.taco.dto.UserCreateDto;
import com.balashovmaksim.taco.dto.UserReadDto;
import com.balashovmaksim.taco.enums.Role;
import com.balashovmaksim.taco.mapper.UserMapper;
import com.balashovmaksim.taco.model.Bucket;
import com.balashovmaksim.taco.model.User;
import com.balashovmaksim.taco.repository.BucketRepository;
import com.balashovmaksim.taco.repository.OrderRepository;
import com.balashovmaksim.taco.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.stream.Collectors;

@Slf4j
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

        User user = userMapper.toUser(userCreateDto);
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        user.setRole(Role.USER);

        user = userRepository.save(user);

        Bucket bucket = new Bucket();
        bucket.setUser(user);
        bucket = bucketRepository.save(bucket);

        user.setBucket(bucket);
        userRepository.save(user);
    }

    @Transactional
    public UserReadDto getUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with name " + username));

        List<OrderSummaryDto> orderSummaries = orderRepository.findByUserId(user.getId()).stream()
                .map(order -> OrderSummaryDto.builder()
                        .id(order.getId())
                        .placedAt(order.getPlacedAt())
                        .build())
                .collect(Collectors.toList());

        UserReadDto userReadDto = userMapper.toUserReadDto(user);
        userReadDto.setOrders(orderSummaries);

        return userReadDto;
    }

    @Transactional
    public void updateProfile(UserReadDto userReadDto) {
        User savedUser = userRepository.findByUsername(userReadDto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found by name " + userReadDto.getUsername()));

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
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
