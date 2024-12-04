package com.example.orderservice.controller;

import com.example.orderservice.helper.MetricsHelper;
import com.example.orderservice.constant.GeneralErrorCode;
import com.example.orderservice.exception.GeneralException;
import com.example.orderservice.model.dto.req.OrderReqDto;
import com.example.orderservice.model.dto.res.OrderResDto;
import com.example.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Tag(name = "OrderController", description = "APIs for order management")
public class OrderController {
    
    private final OrderService orderService;

    @PostMapping(produces = "application/json", consumes = "application/json")
    @Operation(summary = "Post API to create a new order")
    @CircuitBreaker(name = "orderService", fallbackMethod = "createOrderFallback")
    public ResponseEntity<OrderResDto> createOrder(@RequestBody @Valid OrderReqDto orderReqDto) {
        log.info("OrderController createOrder()");
        long startTime = System.currentTimeMillis();
        try {
            MetricsHelper.captureOrderRequestMetric();
            OrderResDto orderResDto = orderService.createOrder(orderReqDto);
            MetricsHelper.captureOrderSuccessMetric();
            return ResponseEntity.ok(orderResDto);
        } finally {
            long endTime = System.currentTimeMillis();
            long responseTime = endTime - startTime;
            MetricsHelper.captureOrderRequestResponseTimeMetric(responseTime);
            log.info("createOrder processed in {}ms", responseTime);
        }
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "Get API to fetch list of all orders")
    public ResponseEntity<List<OrderResDto>> getOrders() {
        log.info("OrderController getOrders()");
        long startTime = System.currentTimeMillis();
        try {
            MetricsHelper.captureGetOrderMetric();
            return ResponseEntity.ok(orderService.getOrders());
        } finally {
            long endTime = System.currentTimeMillis();
            long responseTime = endTime - startTime;
            MetricsHelper.captureGetOrderRequestResponseTimeMetric(responseTime);
            log.info("getOrders processed in {}ms", responseTime);
        }
    }

    private ResponseEntity<OrderResDto> createOrderFallback(Throwable ex) {
        ex.printStackTrace();
        log.info("Order Service is currently unavailable. Order placement failed.");
        MetricsHelper.captureOrderFailureMetric();
        throw new GeneralException(GeneralErrorCode.ORDER_SERVICE_UNAVAILABLE);
    }


}
