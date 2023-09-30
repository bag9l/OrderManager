package com.example.ordermanager.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@Getter
public class NewOrderItem {
    @NotBlank(message = "product it cannot be empty")
    private final String productId;

    @Min(value = 0, message = "quantity cannot be negative")
    private final Integer quantity;
}
