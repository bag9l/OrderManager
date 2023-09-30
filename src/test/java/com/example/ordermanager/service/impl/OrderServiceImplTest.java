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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderItemMapper orderItemMapper;

    @InjectMocks
    private OrderServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetOrderById_ExistingOrder_ReturnsOrderDto() {
        // Arrange
        String orderId = "1";
        Order order = new Order();
        OrderDto expectedOrderDto = new OrderDto(Collections.emptyList(), BigDecimal.ZERO);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderMapper.orderToDto(order)).thenReturn(expectedOrderDto);

        // Act
        OrderDto result = underTest.getOrderById(orderId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedOrderDto, result);
    }

    @Test
    void testGetOrderById_NonExistingOrder_ThrowsEntityNotExistsException() {
        // Arrange
        String orderId = "1";

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityNotExistsException.class, () -> underTest.getOrderById(orderId));
    }

    @Test
    void testCreateOrder_ValidInput_ReturnsCreatedOrder() {
        // Arrange
        NewOrder newOrder = new NewOrder(Collections.emptyList());
        String ownerLogin = "user";
        Client owner = new Client();
        Set<OrderItem> orderItems = new HashSet<>();
        Order createdOrder = new Order(orderItems, owner);

        when(clientRepository.findClientByLogin(ownerLogin)).thenReturn(Optional.of(owner));
        when(orderItemMapper.newToOrderItem(any())).thenReturn(new OrderItem());
        when(orderRepository.save(any())).thenReturn(createdOrder);

        // Act
        Order result = underTest.createOrder(newOrder, ownerLogin);

        // Assert
        assertNotNull(result);
        assertEquals(createdOrder, result);
    }

    @Test
    void testCreateOrder_InvalidOwnerLogin_ThrowsEntityNotExistsException() {
        // Arrange
        NewOrder newOrder = new NewOrder(Collections.emptyList());
        String ownerLogin = "nonexistentuser";

        when(clientRepository.findClientByLogin(ownerLogin)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityNotExistsException.class, () -> underTest.createOrder(newOrder, ownerLogin));
    }

    @Test
    void testDeleteOldUnpaidOrders_DeletesOldUnpaidOrders() {
        // Arrange
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);

        // Act
        underTest.deleteOldUnpaidOrders();

        // Assert
        verify(orderRepository).deleteByCreatedAtBeforeAndIsPaidIsFalse(tenMinutesAgo);
    }

    @Test
    void testPayForOrderById_ValidOrder_ReturnsPaidOrder() {
        // Arrange
        String orderId = "1";
        Order order = new Order();
        order.setIsPaid(false);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        // Act
        Order result = underTest.payForOrderById(orderId);

        // Assert
        assertNotNull(result);
        assertTrue(result.getIsPaid());
    }

    @Test
    void testPayForOrderById_AlreadyPaidOrder_ThrowsPermissionException() {
        // Arrange
        String orderId = "1";
        Order order = new Order();
        order.setIsPaid(true);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Act and Assert
        assertThrows(PermissionException.class, () -> underTest.payForOrderById(orderId));
    }

    @Test
    void testPayForOrderById_NonExistingOrder_ThrowsEntityNotExistsException() {
        // Arrange
        String orderId = "1";

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityNotExistsException.class, () -> underTest.payForOrderById(orderId));
    }
}
