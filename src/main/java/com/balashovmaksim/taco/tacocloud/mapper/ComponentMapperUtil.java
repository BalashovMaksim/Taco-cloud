package com.balashovmaksim.taco.tacocloud.mapper;

import com.balashovmaksim.taco.tacocloud.repository.IngredientRepository;
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
