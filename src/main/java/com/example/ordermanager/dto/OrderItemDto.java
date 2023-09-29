package com.example.ordermanager.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public class OrderItemDto {
    private final String productId;
    private final String name;
    private final BigDecimal price;
    private final Integer orderedQuantity;
}
