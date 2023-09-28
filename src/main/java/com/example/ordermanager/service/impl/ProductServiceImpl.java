package com.example.ordermanager.service.impl;

import com.example.ordermanager.dto.NewProduct;
import com.example.ordermanager.dto.ProductDto;
import com.example.ordermanager.exception.EntityNotExistsException;
import com.example.ordermanager.mapper.ProductMapper;
import com.example.ordermanager.model.Product;
import com.example.ordermanager.repository.ProductRepository;
import com.example.ordermanager.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;


    @Override
    public ProductDto getProductById(String id) {
        return productMapper.productToDto(
                productRepository.findById(id).orElseThrow(() -> {
                    throw new EntityNotExistsException("Product with id:" + id + " not found");
                }));
    }

    @Override
    public List<ProductDto> getProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::productToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto createProduct(NewProduct newProduct) {
        Product product = productMapper.newProductToProduct(newProduct);

        product = productRepository.save(product);

        return productMapper.productToDto(product);
    }
}
