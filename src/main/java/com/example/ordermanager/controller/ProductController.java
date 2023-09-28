package com.example.ordermanager.controller;

import com.example.ordermanager.dto.NewProduct;
import com.example.ordermanager.dto.ProductDto;
import com.example.ordermanager.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("products")
@RestController()
public class ProductController {

    private final ProductService productService;


    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                productService.getProductById(id)
        );
    }

    @GetMapping()
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(
                productService.getProducts()
        );
    }

    @PreAuthorize(value = "hasAuthority('MANAGER')")
    @PostMapping()
    public ResponseEntity<ProductDto> createProduct(@RequestBody NewProduct newProduct) {
        return ResponseEntity.status(HttpStatus.OK).body(
                productService.createProduct(newProduct)
        );
    }
}
