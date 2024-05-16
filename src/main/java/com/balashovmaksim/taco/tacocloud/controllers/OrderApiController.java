package com.balashovmaksim.taco.tacocloud.controllers;

import com.balashovmaksim.taco.tacocloud.dto.OrderCreateDto;
import com.balashovmaksim.taco.tacocloud.dto.OrderReadDto;

import com.balashovmaksim.taco.tacocloud.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/orders", produces = "application/json")
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderService orderService;
    @GetMapping("/all")
    public List<OrderReadDto> getAllOrder(){
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderReadDto getOrderById(@PathVariable("id") Long id){
        return orderService.getOrderById(id);
    }

    @PostMapping("/create")
    public OrderReadDto createOrder(@RequestBody OrderCreateDto orderCreateDto){
        return orderService.createOrder(orderCreateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderById(@PathVariable("id") Long id){
        orderService.deleteOrderById(id);
    }

}
