package com.balashovmaksim.taco.model;

import com.balashovmaksim.taco.enums.Type;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "ingredient")
@Data
public class Ingredient {

    @Id
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;

    @Column(name = "price", nullable = false)
    private Double price;
}
