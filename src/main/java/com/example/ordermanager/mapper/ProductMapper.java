package com.example.ordermanager.mapper;

import com.example.ordermanager.dto.NewProduct;
import com.example.ordermanager.dto.ProductDto;
import com.example.ordermanager.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ProductMapper {

    public abstract ProductDto productToDto(Product product);

    public abstract Product newProductToProduct(NewProduct newProduct);
}
