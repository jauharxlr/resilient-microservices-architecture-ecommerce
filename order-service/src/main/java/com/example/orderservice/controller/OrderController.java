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

@Slf4j
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Tag(name = "OrderController", description = "APIs for order management")
public class OrderController {
    
    private final OrderService orderService;

    @PostMapping(produces = "application/json", consumes = "application/json")
    @Operation(summary = "Post API to create a new order")
    @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
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
            log.info("createOrder completed in {}ms", responseTime);
        }
    }

    private ResponseEntity<OrderResDto> paymentFallback(Throwable ex) {
        log.info("Payment Service is currently unavailable. Order placement failed.");
        MetricsHelper.captureOrderFailureMetric();
        throw new GeneralException(GeneralErrorCode.PAYMENT_SERVICE_UNAVAILABLE);
    }


}
