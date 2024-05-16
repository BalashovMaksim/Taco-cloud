package com.balashovmaksim.taco.tacocloud.mapper;

import com.balashovmaksim.taco.tacocloud.dto.OrderCreateDto;
import com.balashovmaksim.taco.tacocloud.dto.OrderReadDto;
import com.balashovmaksim.taco.tacocloud.model.TacoOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderReadDto toDto(TacoOrder tacoOrder);

    TacoOrder toEntity(OrderCreateDto orderCreateDto);
}