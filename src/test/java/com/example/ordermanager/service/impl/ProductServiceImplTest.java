package com.example.ordermanager.service.impl;

import com.example.ordermanager.dto.NewProduct;
import com.example.ordermanager.dto.ProductDto;
import com.example.ordermanager.exception.EntityNotExistsException;
import com.example.ordermanager.mapper.ProductMapper;
import com.example.ordermanager.model.Product;
import com.example.ordermanager.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetProductById_ExistingProduct_ReturnsProductDto() {
        // Arrange
        String productId = "1";
        Product product = new Product("1", "Test Product", BigDecimal.valueOf(10.99), 5);
        ProductDto expectedProductDto = new ProductDto("1", "Test Product", BigDecimal.valueOf(10.99), 5);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.productToDto(product)).thenReturn(expectedProductDto);

        // Act
        ProductDto result = underTest.getProductById(productId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedProductDto, result);
    }

    @Test
    void testGetProductById_NonExistingProduct_ThrowsEntityNotExistsException() {
        // Arrange
        String productId = "1";

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityNotExistsException.class, () -> underTest.getProductById(productId));
    }

    @Test
    void testGetProducts_ReturnsListOfProductDto() {
        // Arrange
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("1", "Product 1", BigDecimal.valueOf(10.99), 5));
        productList.add(new Product("2", "Product 2", BigDecimal.valueOf(15.49), 10));

        List<ProductDto> expectedProductDtos = new ArrayList<>();
        expectedProductDtos.add(new ProductDto("1", "Product 1", BigDecimal.valueOf(10.99), 5));
        expectedProductDtos.add(new ProductDto("2", "Product 2", BigDecimal.valueOf(15.49), 10));

        when(productRepository.findAll()).thenReturn(productList);
        when(productMapper.productToDto(any())).thenAnswer(invocation -> {
            Product product = invocation.getArgument(0);
            return expectedProductDtos.stream()
                    .filter(dto -> dto.getId().equals(product.getId()))
                    .findFirst()
                    .orElse(null);
        });

        // Act
        List<ProductDto> result = underTest.getProducts();

        // Assert
        assertNotNull(result);
        assertEquals(expectedProductDtos.size(), result.size());
        assertTrue(result.containsAll(expectedProductDtos));
    }

    @Test
    void testCreateProduct_ValidInput_ReturnsCreatedProductDto() {
        // Arrange
        NewProduct newProduct = new NewProduct("New Product", BigDecimal.valueOf(20.99), 15);
        Product createdProduct = new Product("1", "New Product", BigDecimal.valueOf(20.99), 15);
        ProductDto expectedProductDto = new ProductDto("1", "New Product", BigDecimal.valueOf(20.99), 15);

        when(productMapper.newProductToProduct(newProduct)).thenReturn(createdProduct);
        when(productRepository.save(createdProduct)).thenReturn(createdProduct);
        when(productMapper.productToDto(createdProduct)).thenReturn(expectedProductDto);

        // Act
        ProductDto result = underTest.createProduct(newProduct);

        // Assert
        assertNotNull(result);
        assertEquals(expectedProductDto, result);
    }
}
