package com.example.ordermanager.mapper;

import com.example.ordermanager.dto.OrderDto;
import com.example.ordermanager.dto.OrderItemDto;
import com.example.ordermanager.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {OrderItemMapper.class})
public abstract class OrderMapper {

    protected OrderItemMapper orderItemMapper;

    public OrderDto orderToDto(Order order) {
        List<OrderItemDto> orderItemDtos = order.getProductItems().stream()
                .map(orderItemMapper::orderItemToDto)
                .collect(Collectors.toList());

        BigDecimal totalPrice = BigDecimal.valueOf(0);

        orderItemDtos.forEach((item) -> totalPrice.add(item.getPrice()));

        return new OrderDto(orderItemDtos, totalPrice);
    }

    @Autowired
    public void setOrderItemMapper(OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }
}
