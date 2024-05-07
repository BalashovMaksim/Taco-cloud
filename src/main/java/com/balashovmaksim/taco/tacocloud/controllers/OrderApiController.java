package com.balashovmaksim.taco.tacocloud.controllers;

import com.balashovmaksim.taco.tacocloud.model.TacoOrder;
import com.balashovmaksim.taco.tacocloud.repository.OrderRepository;
import com.balashovmaksim.taco.tacocloud.service.OrderMessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/orders", produces = "application/json")
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;
    private final OrderMessagingService orderMessagingService;
    @GetMapping("/all")
    public List<TacoOrder> getAllOrder(){
        return orderRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<TacoOrder> getOrderById(@PathVariable("id") Long orderId){
        Optional<TacoOrder> optionalOrder = orderRepository.findById(orderId);
        return optionalOrder.map(tacoOrder -> new ResponseEntity<>(tacoOrder, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public TacoOrder postOrder(@RequestBody TacoOrder tacoOrder){
        orderMessagingService.sendOrder(tacoOrder);
        return orderRepository.save(tacoOrder);
    }


    @PatchMapping(path = "/{orderId}", consumes = "application/json")
    public TacoOrder updateOrder(@PathVariable("orderId") Long orderId,
                                 @RequestBody TacoOrder patch){
        TacoOrder order = orderRepository.findById(orderId).get();
        if(patch.getDeliveryName() != null){
            order.setDeliveryName(patch.getDeliveryName());
        }
        if(patch.getDeliveryStreet() != null){
            order.setDeliveryStreet(patch.getDeliveryStreet());
        }
        if(patch.getDeliveryCity() != null){
            order.setDeliveryCity(patch.getDeliveryCity());
        }
        if(patch.getDeliveryState() != null){
            order.setDeliveryState(patch.getDeliveryState());
        }
        if(patch.getDeliveryZip() != null){
            order.setDeliveryZip(patch.getDeliveryZip());
        }
        if(patch.getCcNumber() != null){
            order.setCcNumber(patch.getCcNumber());
        }
        if(patch.getCcExpiration() != null){
            order.setCcExpiration(patch.getCcExpiration());
        }
        if(patch.getCcCVV() != null){
            order.setCcCVV(patch.getCcCVV());
        }
        return orderRepository.save(order);
    }
    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long orderId) throws EmptyResultDataAccessException {
        Optional<TacoOrder> order = orderRepository.findById(orderId);
        if(order.isPresent()){
            orderRepository.deleteById(orderId);
        }
    }


}
