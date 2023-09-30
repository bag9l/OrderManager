package com.example.ordermanager.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public class OrderItemDto {
    private final String productId;
    private final String name;
    private final BigDecimal priceOfProduct;
    private final Integer orderedQuantity;
}
