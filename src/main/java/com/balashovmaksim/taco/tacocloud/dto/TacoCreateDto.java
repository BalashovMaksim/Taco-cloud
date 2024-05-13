package com.balashovmaksim.taco.tacocloud.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TacoCreateDto {
    private String name;
    private List<String> ingredientIds;
}
