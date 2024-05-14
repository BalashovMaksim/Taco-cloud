package com.balashovmaksim.taco.tacocloud.mapper;

import com.balashovmaksim.taco.tacocloud.dto.IngredientCreateDto;
import com.balashovmaksim.taco.tacocloud.dto.IngredientReadDto;
import com.balashovmaksim.taco.tacocloud.dto.IngredientUpdateDto;
import com.balashovmaksim.taco.tacocloud.model.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    IngredientReadDto toDto(Ingredient ingredient);

    Ingredient toEntity(IngredientCreateDto ingredientCreateDto);

}