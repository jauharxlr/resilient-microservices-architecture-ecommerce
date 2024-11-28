package com.example.orderservice.controller;

import com.example.orderservice.config.MetricsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final MetricsConfig metricsConfig;

    @GetMapping
    public ResponseEntity<String> createOrder() {
        long startTime = System.currentTimeMillis();
        try {
            metricsConfig.getOrderRequestCounter().increment();
            return ResponseEntity.ok("Hello");
        } finally {
            long endTime = System.currentTimeMillis();
            long responseTime = endTime - startTime;
            metricsConfig.setOrderRequestResponseTimeGuage(responseTime);
        }
    }
}
