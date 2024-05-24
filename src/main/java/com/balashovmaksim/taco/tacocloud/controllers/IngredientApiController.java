package com.balashovmaksim.taco.tacocloud.controllers;

import com.balashovmaksim.taco.tacocloud.dto.IngredientCreateDto;
import com.balashovmaksim.taco.tacocloud.dto.IngredientReadDto;
import com.balashovmaksim.taco.tacocloud.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/ingredients", produces = "application/json")
@RequiredArgsConstructor
public class IngredientApiController {
    private final IngredientService ingredientService;

    @GetMapping("/all")
    public List<IngredientReadDto> getAllIngredients(){
        return ingredientService.getAllIngredients();
    }

    @GetMapping("/{id}")
    public IngredientReadDto getIngredientById(@PathVariable("id") String id){
        return ingredientService.getIngredientById(id);
    }

    @PostMapping("/create")
    public IngredientReadDto createIngredient(@RequestBody IngredientCreateDto ingredientCreateDto){
        return ingredientService.createIngredient(ingredientCreateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") String id){
        ingredientService.deleteById(id);
    }
}
