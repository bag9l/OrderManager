package com.example.ordermanager.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class NewOrderItem {
    private final String productId;
    private final Integer quantity;
}
