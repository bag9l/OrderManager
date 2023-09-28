package com.example.ordermanager.mapper;

import com.example.ordermanager.dto.NewOrderItem;
import com.example.ordermanager.exception.EntityNotExistsException;
import com.example.ordermanager.exception.OutOfQuantityException;
import com.example.ordermanager.model.OrderItem;
import com.example.ordermanager.model.Product;
import com.example.ordermanager.repository.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {ProductRepository.class})
public abstract class OrderItemMapper {

    protected ProductRepository productRepository;


    public OrderItem newToOrderItem(NewOrderItem newOrderItem) {
        Product product = productRepository.findById(newOrderItem.getProductId()).orElseThrow(() -> {
            throw new EntityNotExistsException("Product with id:" + newOrderItem.getProductId() + " not found");
        });
        Integer quantity = newOrderItem.getQuantity();

        if (quantity > product.getQuantity()) {
            throw new OutOfQuantityException("There are not enough products in stock for this order");
        }

        return new OrderItem(product, quantity);
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
