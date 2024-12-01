package com.example.orderservice.controller;

import com.example.orderservice.config.MetricsConfig;
import com.example.orderservice.constant.GeneralErrorCode;
import com.example.orderservice.exception.GeneralException;
import com.example.orderservice.model.dto.req.OrderReqDto;
import com.example.orderservice.model.dto.res.OrderResDto;
import com.example.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final MetricsConfig metricsConfig;
    private final OrderService orderService;

    @PostMapping
    @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
    public ResponseEntity<OrderResDto> createOrder(@RequestBody @Valid OrderReqDto orderReqDto) {
        log.info("OrderController createOrder()");
        long startTime = System.currentTimeMillis();
        try {
            metricsConfig.captureOrderRequestMetric();
            OrderResDto orderResDto = orderService.createOrder(orderReqDto);
            return ResponseEntity.ok(orderResDto);
        } finally {
            long endTime = System.currentTimeMillis();
            long responseTime = endTime - startTime;
            metricsConfig.captureOrderRequestResponseTimeMetric(responseTime);
            log.info("createOrder completed in {}ms", responseTime);
        }
    }

    private ResponseEntity<OrderResDto> paymentFallback(Throwable ex) {
        log.info("Payment Service is currently unavailable. Order placement failed.");
        throw new GeneralException(GeneralErrorCode.PAYMENT_SERVICE_UNAVAILABLE);
    }


}
