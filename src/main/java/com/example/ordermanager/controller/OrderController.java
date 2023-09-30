package com.example.ordermanager.controller;

import com.example.ordermanager.dto.NewOrder;
import com.example.ordermanager.dto.OrderDto;
import com.example.ordermanager.model.Order;
import com.example.ordermanager.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("orders")
@RestController()
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize(value = "hasAuthority('CLIENT')")
    @PostMapping
    public ResponseEntity<Order> createOrder(@AuthenticationPrincipal UserDetails userDetails,
                                             @RequestBody NewOrder newOrder) {
        return ResponseEntity.status(HttpStatus.OK).body(
                orderService.createOrder(newOrder, userDetails.getUsername())
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                orderService.getOrderById(id)
        );
    }

    @PutMapping("{id}/pay")
    public ResponseEntity<Order> payForOrder(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                orderService.payForOrderById(id)
        );
    }
}
