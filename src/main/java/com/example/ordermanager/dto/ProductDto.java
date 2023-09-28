package com.example.ordermanager.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public class ProductDto {

    private final String id;

    private final String name;

    private final BigDecimal price;

    private final Integer quantity;
}
