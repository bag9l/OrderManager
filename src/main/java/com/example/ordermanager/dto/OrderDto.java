package com.example.ordermanager.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class OrderDto {
    private final List<OrderItemDto> orderItems;
    private final BigDecimal totalPrice;
}
