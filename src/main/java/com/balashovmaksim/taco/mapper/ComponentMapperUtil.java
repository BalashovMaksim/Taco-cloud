package com.balashovmaksim.taco.mapper;

import com.balashovmaksim.taco.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComponentMapperUtil {

    private static IngredientRepository ingredientRepository;

    @Autowired
    public ComponentMapperUtil(IngredientRepository repo) {
        ingredientRepository = repo;
    }

    public static IngredientRepository getIngredientRepository() {
        return ingredientRepository;
    }
}
