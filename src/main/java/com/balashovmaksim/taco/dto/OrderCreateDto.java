package com.balashovmaksim.taco.dto;

import com.balashovmaksim.taco.model.Taco;
import com.balashovmaksim.taco.model.User;
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
