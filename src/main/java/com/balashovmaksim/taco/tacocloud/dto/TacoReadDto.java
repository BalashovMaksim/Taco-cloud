package com.balashovmaksim.taco.tacocloud.dto;

import com.balashovmaksim.taco.tacocloud.model.Ingredient;
import com.balashovmaksim.taco.tacocloud.model.Taco;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TacoReadDto {
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    private List<String> ingredientIds;
}
