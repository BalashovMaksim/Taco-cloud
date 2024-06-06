package com.balashovmaksim.taco.controllers;

import com.balashovmaksim.taco.dto.TacoCreateDto;
import com.balashovmaksim.taco.dto.TacoReadDto;
import com.balashovmaksim.taco.dto.TacoUpdateDto;
import com.balashovmaksim.taco.service.TacoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/api/tacos",
                produces = "application/json")
@RequiredArgsConstructor
public class TacoApiController {
    private final TacoService tacoService;
    private static final Logger logger = LoggerFactory.getLogger(TacoApiController.class);

    @GetMapping("/all")
    public List<TacoReadDto> getAllTaco(){
        logger.info("Getting all tacos");
        return tacoService.getAll();
    }

    @GetMapping("{id}")
    public TacoReadDto getTacoById(@PathVariable("id") Long id){
        logger.info("Getting taco by id: {}", id);
        return tacoService.getTacoById(id);
    }

    @PostMapping("/create")
    public TacoReadDto createTaco(@RequestBody TacoCreateDto tacoCreateDto){
        logger.info("Creating new taco");
        return tacoService.createTaco(tacoCreateDto);
    }

    @PutMapping("/{id}")
    public TacoReadDto updateTaco(@PathVariable("id") Long id, @RequestBody TacoUpdateDto tacoUpdateDto){
        logger.info("Updating taco with id: {}", id);
        return tacoService.updateTaco(id, tacoUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTacoById(@PathVariable("id") Long id){
        logger.info("Deleting taco with id: {}", id);
        tacoService.deleteTacoById(id);
    }
}
