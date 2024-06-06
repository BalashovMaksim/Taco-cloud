package com.balashovmaksim.taco.service;

import com.balashovmaksim.taco.dto.OrderCreateDto;
import com.balashovmaksim.taco.dto.OrderDetailsDto;
import com.balashovmaksim.taco.dto.OrderReadDto;
import com.balashovmaksim.taco.dto.UserReadDto;
import com.balashovmaksim.taco.mapper.OrderMapper;
import com.balashovmaksim.taco.mapper.UserMapper;
import com.balashovmaksim.taco.model.TacoOrder;
import com.balashovmaksim.taco.model.User;
import com.balashovmaksim.taco.repository.OrderRepository;
import com.balashovmaksim.taco.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;

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

        return orderMapper.toOrderDetailsDto(order);
    }

    public UserReadDto getUserDetails(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return userMapper.toUserReadDto(user);
    }

}
