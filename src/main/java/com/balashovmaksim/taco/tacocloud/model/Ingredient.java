package com.balashovmaksim.taco.tacocloud.model;

import com.balashovmaksim.taco.tacocloud.enums.Type;
import jakarta.persistence.*;
import lombok.*;


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
}
