package com.example.ordermanager.repository;

import com.example.ordermanager.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;

public interface OrderRepository extends JpaRepository<Order,String> {

    void deleteByCreatedAtBeforeAndIsPayedIsFalse(LocalTime time);
}
