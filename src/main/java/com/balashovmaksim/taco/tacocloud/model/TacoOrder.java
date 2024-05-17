package com.balashovmaksim.taco.tacocloud.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.stereotype.Service;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "taco_order")
@Entity
public class TacoOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date placedAt = new Date();

    @NotBlank(message = "Delivery name is required")
    @Size(min = 2, message = "Name field must be more than 2 characters long")
    private String deliveryName;

    @NotBlank(message = "Street is required")
    @Size(min = 3, message = "Street address field must be more than 3 characters long")
    private String deliveryStreet;

    @NotBlank(message = "City is required")
    @Size(min = 2, message = "City field must be more than 2 characters long")
    private String deliveryCity;

    @NotBlank(message = "State is required")
    @Size(min = 1, message = "State field must be more than 1 characters long")
    private String deliveryState;

    @NotBlank(message = "Zip code is required")
    @Size(min = 5, message = "Zip field must be more than 5 characters long")
    private String deliveryZip;

    @CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$", message = "Must be formatted MM/YY")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    @Column(name = "cc_cvv")
    private String ccCVV;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "taco_order_id")
    private List<Taco> tacos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
        updateTotalPrice(); // Обновление общей суммы при добавлении тако
    }

    // Метод обновления общей суммы
    public void updateTotalPrice() {
        this.totalPrice = tacos.stream()
                .mapToDouble(Taco::calculatePrice)
                .sum();
    }
}
