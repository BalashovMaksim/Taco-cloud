package com.balashovmaksim.taco.mapper;

import com.balashovmaksim.taco.dto.IngredientCreateDto;
import com.balashovmaksim.taco.dto.IngredientReadDto;
import com.balashovmaksim.taco.model.Ingredient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    IngredientReadDto toDto(Ingredient ingredient);

    Ingredient toEntity(IngredientCreateDto ingredientCreateDto);

}