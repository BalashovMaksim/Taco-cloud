package com.balashovmaksim.taco.tacocloud.mapper;

import com.balashovmaksim.taco.tacocloud.dto.TacoReadDto;
import com.balashovmaksim.taco.tacocloud.model.Ingredient;
import com.balashovmaksim.taco.tacocloud.model.Taco;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TacoMapper {

    @Mapping(target = "ingredientIds", expression = "java(mapIngredientIds(taco.getIngredients()))")
    TacoReadDto toDto(Taco taco);

    default List<String> mapIngredientIds(List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(Ingredient::getId)
                .collect(Collectors.toList());
    }
}
