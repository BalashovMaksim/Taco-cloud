package com.balashovmaksim.taco.tacocloud.service;

import com.balashovmaksim.taco.tacocloud.dto.IngredientCreateDto;
import com.balashovmaksim.taco.tacocloud.dto.IngredientReadDto;
import com.balashovmaksim.taco.tacocloud.mapper.IngredientMapper;
import com.balashovmaksim.taco.tacocloud.model.Ingredient;
import com.balashovmaksim.taco.tacocloud.repository.IngredientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    @Transactional
    public List<IngredientReadDto> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        return ingredients.stream()
                .map(ingredientMapper::toDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public IngredientReadDto getIngredientById(String id) {
        Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ingredient not found"));
        return ingredientMapper.toDto(ingredient);
    }

    @Transactional
    public IngredientReadDto createIngredient(IngredientCreateDto ingredientCreateDto) {
        Ingredient ingredient = ingredientMapper.toEntity(ingredientCreateDto);
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return ingredientMapper.toDto(savedIngredient);
    }


    public void deleteById(String id) {
        ingredientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ingredient not found or deleted"));
        ingredientRepository.deleteById(id);
    }
}
