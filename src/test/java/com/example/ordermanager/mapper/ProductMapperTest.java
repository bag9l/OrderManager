package com.example.ordermanager.mapper;

import com.example.ordermanager.dto.NewProduct;
import com.example.ordermanager.dto.ProductDto;
import com.example.ordermanager.model.Product;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductMapperTest {

    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Test
    void testProductToDto() {
        // Arrange
        Product product = new Product("1", "Product 1", BigDecimal.valueOf(10), 20);

        // Act
        ProductDto productDto = productMapper.productToDto(product);

        // Assert
        assertEquals(product.getId(), productDto.getId());
        assertEquals(product.getName(), productDto.getName());
        assertEquals(product.getPrice(), productDto.getPrice());
        assertEquals(product.getQuantity(), productDto.getQuantity());
    }

    @Test
    void testNewProductToProduct() {
        // Arrange
        NewProduct newProduct = new NewProduct("New Product", BigDecimal.valueOf(15), 30);

        // Act
        Product product = productMapper.newProductToProduct(newProduct);

        // Assert
        assertEquals(newProduct.getName(), product.getName());
        assertEquals(newProduct.getPrice(), product.getPrice());
        assertEquals(newProduct.getQuantity(), product.getQuantity());
    }
}
