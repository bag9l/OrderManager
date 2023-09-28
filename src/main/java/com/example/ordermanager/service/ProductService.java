package com.example.ordermanager.service;

import com.example.ordermanager.dto.NewProduct;
import com.example.ordermanager.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto getProductById(String id);

    List<ProductDto> getProducts();

    ProductDto createProduct(NewProduct newProduct);
}
