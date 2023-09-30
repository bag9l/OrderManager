package com.example.ordermanager.repository;

import com.example.ordermanager.model.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }


    @Test
    void shouldDeleteOrdersWhenOldAndUnpaid() {
        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now().minusMinutes(10));
        order.setIsPaid(false);
        underTest.save(order);

        underTest.deleteByCreatedAtBeforeAndIsPaidIsFalse(LocalDateTime.now());

        List<Order> orderFromDb = underTest.findAll();

        assertThat(orderFromDb.size()).isEqualTo(0);
    }

    @Test
    void shouldNotDeleteOrdersWhenOldButPaid() {
        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now().minusMinutes(10));
        order.setIsPaid(true);
        underTest.save(order);

        underTest.deleteByCreatedAtBeforeAndIsPaidIsFalse(LocalDateTime.now());

        List<Order> orderFromDb = underTest.findAll();

        assertThat(orderFromDb.size()).isEqualTo(1);
    }
}