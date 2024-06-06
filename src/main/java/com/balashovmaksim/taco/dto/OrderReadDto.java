package com.balashovmaksim.taco.dto;

import com.balashovmaksim.taco.model.Taco;
import com.balashovmaksim.taco.model.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderReadDto {
    private Long id;
    private Date placedAt;
    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;
    private List<Taco> tacos;
    private User user;
    private Double totalPrice;
}
