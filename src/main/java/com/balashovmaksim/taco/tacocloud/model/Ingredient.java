package com.balashovmaksim.taco.tacocloud.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@Data
@Table(name = "ingredient")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    @Id
    private String id;
    private String name;
    private Type type;
    public enum Type {
        WRAP,PROTEIN,VEGGIES,CHEESE,SAUCE
    }
}
