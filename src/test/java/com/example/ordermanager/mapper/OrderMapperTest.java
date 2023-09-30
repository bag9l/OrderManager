package com.example.ordermanager.mapper;

import com.example.ordermanager.dto.OrderDto;
import com.example.ordermanager.dto.OrderItemDto;
import com.example.ordermanager.model.Order;
import com.example.ordermanager.model.OrderItem;
import com.example.ordermanager.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class OrderMapperTest {

    @Mock
    private OrderItemMapper orderItemMapper;

    @InjectMocks
    private OrderMapperImpl orderMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testOrderToDto() {
        // Arrange
        OrderItemDto orderItemDto1 = new OrderItemDto("productId1", "Product 1", BigDecimal.valueOf(10), 2);
        OrderItemDto orderItemDto2 = new OrderItemDto("productId2", "Product 2", BigDecimal.valueOf(20), 3);
        List<OrderItemDto> orderItemDtos = Arrays.asList(orderItemDto1, orderItemDto2);

        BigDecimal totalPrice = BigDecimal.valueOf(2 * 10 + 3 * 20);

        when(orderItemMapper.orderItemToDto(any())).thenReturn(orderItemDto1, orderItemDto2);

        Order order = new Order();
        Set<OrderItem> orderItems = orderItemDtos.stream()
                .map(dto -> new OrderItem(new Product(dto.getProductId(), dto.getName(), dto.getPriceOfProduct(), dto.getOrderedQuantity()), dto.getOrderedQuantity()))
                .collect(Collectors.toSet());
        order.setProductItems(orderItems);

        // Act
        OrderDto orderDto = orderMapper.orderToDto(order);

        // Assert
        assertEquals(orderItemDtos.size(), orderDto.getOrderItems().size());
        assertEquals(totalPrice, orderDto.getTotalPrice());
    }
}
