package com.balashovmaksim.taco.tacocloud.service;

import com.balashovmaksim.taco.tacocloud.dto.*;
import com.balashovmaksim.taco.tacocloud.mapper.OrderMapper;
import com.balashovmaksim.taco.tacocloud.model.Ingredient;
import com.balashovmaksim.taco.tacocloud.model.TacoOrder;
import com.balashovmaksim.taco.tacocloud.model.User;
import com.balashovmaksim.taco.tacocloud.repository.OrderRepository;
import com.balashovmaksim.taco.tacocloud.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public List<OrderReadDto> getAllOrders() {
        List<TacoOrder> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderReadDto getOrderById(Long id) {
        TacoOrder order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return orderMapper.toDto(order);
    }

    @Transactional
    public OrderReadDto createOrder(OrderCreateDto orderCreateDto) {
        TacoOrder order = orderMapper.toEntity(orderCreateDto);
        TacoOrder orderSaved = orderRepository.save(order);
        return orderMapper.toDto(orderSaved);
    }

    @Transactional
    public void deleteOrderById(Long id) {
        orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found or deleted"));
        orderRepository.deleteById(id);
    }

    @Transactional
    public boolean processOrder(TacoOrder order, String username, Errors errors) {
        if (errors.hasErrors()) {
            return false;
        }

        User user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        order.setUser(user);
        orderRepository.save(order);
        return true;
    }

    @Transactional
    public OrderDetailsDto getOrderDetails(Long id) {
        TacoOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<TacoReadDto> tacos = order.getTacos().stream()
                .map(taco -> TacoReadDto.builder()
                        .name(taco.getName())
                        .ingredientIds(Collections.singletonList(taco.getIngredients().stream()
                                .map(Ingredient::getName)
                                .collect(Collectors.joining(", "))))
                        .build())
                .collect(Collectors.toList());

        Double totalPrice = order.getTacos().stream()
                .flatMap(taco -> taco.getIngredients().stream())
                .mapToDouble(Ingredient::getPrice)
                .sum();

        return OrderDetailsDto.builder()
                .id(order.getId())
                .placedAt(order.getPlacedAt())
                .tacos(tacos)
                .totalPrice(totalPrice)
                .build();
    }

    public UserReadDto getUserDetails(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));;
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
