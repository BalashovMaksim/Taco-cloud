package com.balashovmaksim.taco.tacocloud.service;

import com.balashovmaksim.taco.tacocloud.model.Ingredient;
import com.balashovmaksim.taco.tacocloud.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import com.balashovmaksim.taco.tacocloud.model.Ingredient.Type;

@Component
@RequiredArgsConstructor
public class IngredientByIdConverter implements Converter<String, Ingredient> {
    private final IngredientRepository ingredientRepository;

    @Override
    public Ingredient convert(String id) {
        return ingredientRepository.findById(id).orElse(null);
    }
}
