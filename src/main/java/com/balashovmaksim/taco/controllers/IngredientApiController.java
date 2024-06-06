package com.balashovmaksim.taco.controllers;

import com.balashovmaksim.taco.dto.IngredientCreateDto;
import com.balashovmaksim.taco.dto.IngredientReadDto;
import com.balashovmaksim.taco.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/ingredients", produces = "application/json")
@RequiredArgsConstructor
public class IngredientApiController {
    private final IngredientService ingredientService;
    private static final Logger logger = LoggerFactory.getLogger(IngredientApiController.class);

    @GetMapping("/all")
    public List<IngredientReadDto> getAllIngredients(){
        logger.info("Getting all ingredients");
        return ingredientService.getAllIngredients();
    }

    @GetMapping("/{id}")
    public IngredientReadDto getIngredientById(@PathVariable("id") String id){
        logger.info("Getting ingredient by id: {}", id);
        return ingredientService.getIngredientById(id);
    }

    @PostMapping("/create")
    public IngredientReadDto createIngredient(@RequestBody IngredientCreateDto ingredientCreateDto){
        logger.info("Creating ingredient: {}", ingredientCreateDto);
        return ingredientService.createIngredient(ingredientCreateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") String id){
        logger.info("Deleting ingredient by id: {}", id);
        ingredientService.deleteById(id);
    }
}
