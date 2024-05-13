package com.balashovmaksim.taco.tacocloud.dto;

import lombok.Data;

import java.util.List;

@Data
public class TacoUpdateDto {
    private String name;
    private List<String> ingredientIds;
}