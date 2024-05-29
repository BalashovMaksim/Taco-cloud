package com.balashovmaksim.taco.tacocloud.mapper;

import com.balashovmaksim.taco.tacocloud.dto.OrderCreateDto;
import com.balashovmaksim.taco.tacocloud.dto.OrderDetailsDto;
import com.balashovmaksim.taco.tacocloud.dto.OrderReadDto;
import com.balashovmaksim.taco.tacocloud.dto.TacoReadDto;
import com.balashovmaksim.taco.tacocloud.model.Ingredient;
import com.balashovmaksim.taco.tacocloud.model.Taco;
import com.balashovmaksim.taco.tacocloud.model.TacoOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderReadDto toDto(TacoOrder tacoOrder);
    TacoOrder toEntity(OrderCreateDto orderCreateDto);

    @Mapping(target = "ingredientIds", source = "ingredients")
    TacoReadDto tacoToTacoReadDto(Taco taco);

    default List<String> mapIngredientsToNames(List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(Ingredient::getName)
                .collect(Collectors.toList());
    }

    default Double calculateTotalPrice(TacoOrder order) {
        return order.getTacos().stream()
                .flatMap(taco -> taco.getIngredients().stream())
                .mapToDouble(Ingredient::getPrice)
                .sum();
    }

    default OrderDetailsDto toOrderDetailsDto(TacoOrder order) {
        List<TacoReadDto> tacos = order.getTacos().stream()
                .map(this::tacoToTacoReadDto)
                .collect(Collectors.toList());

        Double totalPrice = calculateTotalPrice(order);

        return OrderDetailsDto.builder()
                .id(order.getId())
                .placedAt(order.getPlacedAt())
                .tacos(tacos)
                .totalPrice(totalPrice)
                .build();
    }
}