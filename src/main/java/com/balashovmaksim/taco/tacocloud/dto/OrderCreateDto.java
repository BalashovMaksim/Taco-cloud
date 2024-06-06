package com.balashovmaksim.taco.tacocloud.dto;

import com.balashovmaksim.taco.tacocloud.model.Taco;
import com.balashovmaksim.taco.tacocloud.model.User;
import lombok.Data;

import java.util.List;

@Data
public class OrderCreateDto {
    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;
    private List<Taco> tacos;
    private User user;
}
