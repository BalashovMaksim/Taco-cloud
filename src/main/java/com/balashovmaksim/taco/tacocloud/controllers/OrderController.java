package com.balashovmaksim.taco.tacocloud.controllers;

import com.balashovmaksim.taco.tacocloud.dto.OrderDetailsDto;
import com.balashovmaksim.taco.tacocloud.dto.UserReadDto;
import com.balashovmaksim.taco.tacocloud.model.TacoOrder;
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

    @GetMapping("/current")
    public String orderForm(@ModelAttribute TacoOrder tacoOrder, Model model, Principal principal) {
        if (principal == null) {
            return "orderForm";
        }

        UserReadDto userReadDto = orderService.getUserDetails(principal.getName());
        model.addAttribute("userReadDto", userReadDto);
        model.addAttribute("tacoOrder", tacoOrder);

        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid @ModelAttribute TacoOrder tacoOrder, Errors errors,
                               Model model, SessionStatus sessionStatus, Principal principal) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        tacoOrder.updateTotalPrice();
        if (!orderService.processOrder(tacoOrder, principal.getName(), errors)) {
            return "orderForm";
        }
        // Завершаем сессию и очищаем атрибуты
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