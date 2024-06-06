package com.balashovmaksim.taco.tacocloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OrderDetailsDto {
    private Long id;
    private Date placedAt;
    private List<TacoReadDto> tacos;
    private Double totalPrice;
}