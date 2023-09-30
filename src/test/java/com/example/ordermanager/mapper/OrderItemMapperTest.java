package com.example.ordermanager.mapper;

import com.example.ordermanager.dto.NewOrderItem;
import com.example.ordermanager.dto.OrderItemDto;
import com.example.ordermanager.exception.EntityNotExistsException;
import com.example.ordermanager.exception.OutOfQuantityException;
import com.example.ordermanager.mapper.OrderItemMapper;
import com.example.ordermanager.model.OrderItem;
import com.example.ordermanager.model.Product;
import com.example.ordermanager.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderItemMapperTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderItemMapperImpl orderItemMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testNewToOrderItem_Success() {
        // Arrange
        NewOrderItem newOrderItem = new NewOrderItem("productId", 5);
        Product product = new Product("productId", "Product Name", BigDecimal.TEN, 10);
        when(productRepository.findById("productId")).thenReturn(Optional.of(product));

        // Act
        OrderItem orderItem = orderItemMapper.newToOrderItem(newOrderItem);

        // Assert
        assertEquals(newOrderItem.getProductId(), orderItem.getProduct().getId());
        assertEquals(newOrderItem.getQuantity(), orderItem.getQuantity());
    }

    @Test
    void testNewToOrderItem_ProductNotFound() {
        // Arrange
        NewOrderItem newOrderItem = new NewOrderItem("productId", 5);
        when(productRepository.findById("productId")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotExistsException.class, () -> orderItemMapper.newToOrderItem(newOrderItem));
    }

    @Test
    void testNewToOrderItem_OutOfQuantity() {
        // Arrange
        NewOrderItem newOrderItem = new NewOrderItem("productId", 15);
        Product product = new Product("productId", "Product Name", BigDecimal.TEN, 10);
        when(productRepository.findById("productId")).thenReturn(Optional.of(product));

        // Act & Assert
        assertThrows(OutOfQuantityException.class, () -> orderItemMapper.newToOrderItem(newOrderItem));
    }

    @Test
    void testOrderItemToDto_Success() {
        // Arrange
        Product product = new Product("productId", "Product Name", BigDecimal.valueOf(10), 10);
        OrderItem orderItem = new OrderItem(product, 5);

        // Act
        OrderItemDto orderItemDto = orderItemMapper.orderItemToDto(orderItem);

        // Assert
        assertEquals(orderItem.getProduct().getId(), orderItemDto.getProductId());
        assertEquals(orderItem.getProduct().getName(), orderItemDto.getName());
        assertEquals(BigDecimal.valueOf(10), orderItemDto.getPriceOfProduct());
        assertEquals(orderItem.getQuantity(), orderItemDto.getOrderedQuantity());
    }

    @Test
    void testSetProductRepository() {
        // Arrange
        ProductRepository newProductRepository = mock(ProductRepository.class);

        // Act
        orderItemMapper.setProductRepository(newProductRepository);

        // Assert
        assertSame(newProductRepository, orderItemMapper.productRepository);
    }
}

