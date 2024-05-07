package com.balashovmaksim.taco.tacocloud.controllers;

import com.balashovmaksim.taco.tacocloud.model.Taco;
import com.balashovmaksim.taco.tacocloud.model.TacoOrder;
import com.balashovmaksim.taco.tacocloud.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/api/orders",
        produces = "application/json")
@CrossOrigin(origins = "http://tacocloud:8080")
@RequiredArgsConstructor
public class RestOrderController {
    private final OrderRepository orderRepository;

}
