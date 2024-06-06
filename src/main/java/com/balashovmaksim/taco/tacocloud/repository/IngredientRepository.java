package com.balashovmaksim.taco.tacocloud.repository;

import com.balashovmaksim.taco.tacocloud.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IngredientRepository extends JpaRepository<Ingredient,String> {
}
