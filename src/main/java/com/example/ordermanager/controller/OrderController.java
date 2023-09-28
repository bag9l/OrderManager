package com.example.ordermanager.controller;

import com.example.ordermanager.dto.NewOrder;
import com.example.ordermanager.model.Order;
import com.example.ordermanager.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("orders")
@RestController()
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody NewOrder newOrder){
        return ResponseEntity.status(HttpStatus.OK).body(
                orderService.createOrder(newOrder)
        );
    }

    @PutMapping("pay")
    public ResponseEntity<Order> payForOrder(@RequestParam("orderId") String id){
        return ResponseEntity.status(HttpStatus.OK).body(
                orderService.payForOrderById(id)
        );
    }
}
