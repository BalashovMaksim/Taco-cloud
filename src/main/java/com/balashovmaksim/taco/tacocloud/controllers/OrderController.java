package com.balashovmaksim.taco.tacocloud.controllers;

import com.balashovmaksim.taco.tacocloud.dto.OrderDetailsDto;
import com.balashovmaksim.taco.tacocloud.dto.UserReadDto;
import com.balashovmaksim.taco.tacocloud.model.TacoOrder;
import com.balashovmaksim.taco.tacocloud.repository.OrderRepository;
import com.balashovmaksim.taco.tacocloud.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.security.Principal;

@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    @GetMapping("/current")
    public String orderForm(Model model, Principal principal) {
        if (principal == null) {
            return "orderForm";
        }

        UserReadDto userReadDto = orderService.getUserDetails(principal.getName());
        model.addAttribute("userReadDto", userReadDto);
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors,
                               SessionStatus sessionStatus, Principal principal) {
        if (!orderService.processOrder(order, principal.getName(), errors)) {
            return "orderForm";
        }
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String getOrderDetails(@PathVariable Long id, Model model) {
        OrderDetailsDto orderDetailsDto = orderService.getOrderDetails(id);
        model.addAttribute("orderDetailsDto", orderDetailsDto);
        return "orderDetails";
    }
}
