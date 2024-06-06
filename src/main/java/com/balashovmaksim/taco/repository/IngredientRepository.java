package com.balashovmaksim.taco.repository;

import com.balashovmaksim.taco.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IngredientRepository extends JpaRepository<Ingredient,String> {
}
