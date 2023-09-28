package com.example.ordermanager.service.impl;

import com.example.ordermanager.dto.NewOrder;
import com.example.ordermanager.exception.EntityNotExistsException;
import com.example.ordermanager.mapper.OrderItemMapper;
import com.example.ordermanager.model.Order;
import com.example.ordermanager.model.OrderItem;
import com.example.ordermanager.repository.OrderRepository;
import com.example.ordermanager.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemMapper orderItemMapper;

    @Override
    public Order createOrder(NewOrder newOrder) {
        Set<OrderItem> orderItems = newOrder.getOrderItems().stream()
                .map(orderItemMapper::newToOrderItem)
                .collect(Collectors.toSet());

        return orderRepository.save(new Order(orderItems));
    }

    @Transactional
    @Override
    public void deleteOldUnpaidOrders() {
        LocalTime tenMinutesAgo = LocalTime.now().minusMinutes(10);
        orderRepository.deleteByCreatedAtBeforeAndIsPayedIsFalse(tenMinutesAgo);
    }

    @Override
    public Order payForOrderById(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            throw new EntityNotExistsException("Order with id:" + orderId + " not found");
        });

        order.setIsPayed(true);

        return orderRepository.save(order);
    }
}