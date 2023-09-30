package com.example.ordermanager.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class NewOrder {
    @NotNull(message = "order should contain items")
    private final List<NewOrderItem> orderItems;
}
