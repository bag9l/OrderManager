package com.example.ordermanager.service.impl;

import com.example.ordermanager.dto.NewOrder;
import com.example.ordermanager.dto.OrderDto;
import com.example.ordermanager.exception.EntityNotExistsException;
import com.example.ordermanager.exception.PermissionException;
import com.example.ordermanager.mapper.OrderItemMapper;
import com.example.ordermanager.mapper.OrderMapper;
import com.example.ordermanager.model.Order;
import com.example.ordermanager.model.OrderItem;
import com.example.ordermanager.model.user.Client;
import com.example.ordermanager.repository.ClientRepository;
import com.example.ordermanager.repository.OrderRepository;
import com.example.ordermanager.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderDto getOrderById(String id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotExistsException("Order with id:" + id + " not found");
        });

        return orderMapper.orderToDto(order);
    }

    @Override
    public Order createOrder(NewOrder newOrder, String ownerLogin) {
        Set<OrderItem> orderItems = newOrder.getOrderItems().stream()
                .map(orderItemMapper::newToOrderItem)
                .collect(Collectors.toSet());
        Client owner = clientRepository.findClientByLogin(ownerLogin).orElseThrow(() -> {
            throw new EntityNotExistsException("User with login:" + ownerLogin + " not found");
        });

        return orderRepository.save(new Order(orderItems, owner));
    }

    @Transactional
    @Override
    public void deleteOldUnpaidOrders() {
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);
        orderRepository.deleteByCreatedAtBeforeAndIsPaidIsFalse(tenMinutesAgo);
    }

    @Override
    public Order payForOrderById(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            throw new EntityNotExistsException("Order with id:" + orderId + " not found");
        });

        if (order.getIsPaid()) {
            throw new PermissionException("The order has already been paid for");
        }

        order.setIsPaid(true);

        return orderRepository.save(order);
    }
}
