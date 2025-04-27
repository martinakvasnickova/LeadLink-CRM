package com.leadlink.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * DTO pro přijímání dat při vytváření nebo aktualizaci obchodního případu.
 * Obsahuje validační anotace pro kontrolu vstupních dat.
 */

public class CaseRequestDTO {

    @NotBlank(message = "Name must not be blank")
    private String name;

    @PositiveOrZero(message = "Price must be positive or zero")
    private float price;

    public CaseRequestDTO() {}

    public CaseRequestDTO(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
