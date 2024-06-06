package com.balashovmaksim.taco.controllers;

import com.balashovmaksim.taco.dto.OrderCreateDto;
import com.balashovmaksim.taco.dto.OrderReadDto;
import com.balashovmaksim.taco.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/orders", produces = "application/json")
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderService orderService;
    private static final Logger logger = LoggerFactory.getLogger(OrderApiController.class);
    @GetMapping("/all")
    public List<OrderReadDto> getAllOrder(){
        logger.info("Getting All Orders");
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderReadDto getOrderById(@PathVariable("id") Long id){
        logger.info("Getting order by id: {}", id);
        return orderService.getOrderById(id);
    }

    @PostMapping("/create")
    public OrderReadDto createOrder(@RequestBody OrderCreateDto orderCreateDto){
        logger.info("Creating order: {}", orderCreateDto);
        return orderService.createOrder(orderCreateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderById(@PathVariable("id") Long id){
        logger.info("Deleting order by id: {}", id);
        orderService.deleteOrderById(id);
    }

}
