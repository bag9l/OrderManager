package com.example.ordermanager.repository;

import com.example.ordermanager.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface OrderRepository extends JpaRepository<Order, String> {
    void deleteByCreatedAtBeforeAndIsPaidIsFalse(LocalDateTime time);
}
