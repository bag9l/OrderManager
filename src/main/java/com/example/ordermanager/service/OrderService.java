package com.example.ordermanager.service;

import com.example.ordermanager.dto.NewOrder;
import com.example.ordermanager.dto.OrderDto;
import com.example.ordermanager.model.Order;

public interface OrderService {
    OrderDto getOrderById(String id);

    Order createOrder(NewOrder newOrder);

    void deleteOldUnpaidOrders();

    Order payForOrderById(String orderId);
}
