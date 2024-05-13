package com.balashovmaksim.taco.tacocloud.controllers;

import com.balashovmaksim.taco.tacocloud.dto.TacoReadDto;
import com.balashovmaksim.taco.tacocloud.model.Taco;
import com.balashovmaksim.taco.tacocloud.repository.TacoRepository;
import com.balashovmaksim.taco.tacocloud.service.TacoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/tacos",
                produces = "application/json")
@RequiredArgsConstructor
public class TacoApiController {
    private final TacoService tacoService;

    @GetMapping("/all")
    public List<TacoReadDto> getAllTaco(){
        return tacoService.getAll();
    }

}
