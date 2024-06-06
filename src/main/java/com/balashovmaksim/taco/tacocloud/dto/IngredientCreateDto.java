package com.balashovmaksim.taco.tacocloud.dto;

import lombok.Data;

@Data
public class IngredientCreateDto {
    private String id;
    private String name;
    private String type;
    private Double price;
}
