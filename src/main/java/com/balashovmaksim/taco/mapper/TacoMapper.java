package com.balashovmaksim.taco.mapper;

import com.balashovmaksim.taco.dto.TacoCreateDto;
import com.balashovmaksim.taco.dto.TacoReadDto;
import com.balashovmaksim.taco.dto.TacoUpdateDto;
import com.balashovmaksim.taco.model.Ingredient;
import com.balashovmaksim.taco.model.Taco;
import com.balashovmaksim.taco.repository.IngredientRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = IngredientRepository.class)
public interface TacoMapper {

    @Mapping(target = "ingredients", source = "ingredientIds")
    Taco toEntity(TacoCreateDto tacoCreateDto);

    default Ingredient mapIngredientIdToIngredient(String id) {
        IngredientRepository ingredientRepository = ComponentMapperUtil.getIngredientRepository();
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Ingredient with id " + id + " not found!"));
    }

    @Mapping(target = "ingredientIds", expression = "java(mapIngredientIds(taco.getIngredients()))")
    TacoReadDto toDto(Taco taco);

    default List<String> mapIngredientIds(List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(Ingredient::getId)
                .collect(Collectors.toList());
    }

    @Mapping(target = "ingredients", source = "ingredientIds", qualifiedByName = "mapIngredientIdsToIngredients")
    void updateEntity(TacoUpdateDto tacoUpdateDto, @MappingTarget Taco taco);

    @Named("mapIngredientIdsToIngredients")
    default List<Ingredient> mapIngredientIdsToIngredients(List<String> ingredientIds) {
        IngredientRepository ingredientRepository = ComponentMapperUtil.getIngredientRepository();
        return ingredientIds.stream()
                .map(id -> ingredientRepository.findById(id)
                        .orElseThrow(() -> new IllegalStateException("Ingredient with id " + id + " not found!")))
                .collect(Collectors.toList());
    }
}
