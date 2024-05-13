package com.balashovmaksim.taco.tacocloud.mapper;

import com.balashovmaksim.taco.tacocloud.dto.UserReadDto;
import com.balashovmaksim.taco.tacocloud.model.User;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")

public interface UserMapper {
    UserReadDto toDto(User user);
}
