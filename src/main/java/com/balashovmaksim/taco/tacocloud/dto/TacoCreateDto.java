package com.balashovmaksim.taco.tacocloud.dto;

import lombok.Data;

import java.util.List;

@Data
public class TacoCreateDto {
    private String name;
    private List<String> ingredientIds;
}
