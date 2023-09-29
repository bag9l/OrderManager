package com.example.ordermanager.service.impl;

import com.example.ordermanager.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ScheduledTasks {

    private final OrderService orderService;

    @Scheduled(cron = "0 * * * * *")
    public void deleteOldUnpaidOrdersTask() {
        orderService.deleteOldUnpaidOrders();
    }
}
