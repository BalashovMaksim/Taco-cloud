package com.balashovmaksim.taco.tacocloud.service;

import com.balashovmaksim.taco.tacocloud.dto.UserReadDto;
import com.balashovmaksim.taco.tacocloud.model.TacoOrder;
import com.balashovmaksim.taco.tacocloud.model.User;
import com.balashovmaksim.taco.tacocloud.repository.OrderRepository;
import com.balashovmaksim.taco.tacocloud.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    @Transactional
    public boolean processOrder(TacoOrder order, String username, Errors errors) {
        if (errors.hasErrors()) {
            return false;
        }

        User user = userRepository.findByUsername(username);
        order.setUser(user);
        orderRepository.save(order);
        return true;
    }

    public UserReadDto getUserDetails(String username) {
        User user = userRepository.findByUsername(username);
        return UserReadDto.builder()
                .fullname(user.getFullname())
                .street(user.getStreet())
                .city(user.getCity())
                .state(user.getState())
                .zip(user.getZip())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
