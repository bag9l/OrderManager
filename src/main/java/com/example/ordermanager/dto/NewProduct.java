package com.example.ordermanager.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public class NewProduct {

    @Size(min = 2, max = 50, message = "login shouldn't be shorter than 6 and longer than 20")
    @NotBlank(message = "name shouldn't be empty")
    private final String name;

    @Min(value = 0, message = "price cannot be negative")
    private final BigDecimal price;

    @Min(value = 0, message = "quantity cannot be negative")
    private final Integer quantity;
}
