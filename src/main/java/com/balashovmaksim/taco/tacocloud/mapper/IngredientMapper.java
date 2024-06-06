package com.balashovmaksim.taco.tacocloud.mapper;

import com.balashovmaksim.taco.tacocloud.dto.IngredientCreateDto;
import com.balashovmaksim.taco.tacocloud.dto.IngredientReadDto;
import com.balashovmaksim.taco.tacocloud.model.Ingredient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    IngredientReadDto toDto(Ingredient ingredient);

    Ingredient toEntity(IngredientCreateDto ingredientCreateDto);

}