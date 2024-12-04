package com.example.paymentservice.controller;

import com.example.paymentservice.constant.GeneralErrorCode;
import com.example.paymentservice.exception.GeneralException;
import com.example.paymentservice.helper.MetricsHelper;
import com.example.paymentservice.model.dto.req.PaymentReqDto;
import com.example.paymentservice.service.PaymentService;
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
@RequestMapping("/payment")
@RequiredArgsConstructor
@Tag(name = "PaymentController", description = "APIs for payment management")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping(produces = "application/json", consumes = "application/json")
    @Operation(summary = "Post API to initiate a new payment for an order")
    @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
    public ResponseEntity<Void> processPayment(@RequestBody @Valid PaymentReqDto paymentReqDto) {
        log.info("PaymentController - processPayment()");
        long startTime = System.currentTimeMillis();
        try {
            MetricsHelper.capturePaymentRequestMetric();
            paymentService.processPayment(paymentReqDto);
            return ResponseEntity.ok().build();
        } finally {
            long endTime = System.currentTimeMillis();
            long responseTime = endTime - startTime;
            MetricsHelper.captureGetOrderRequestResponseTimeMetric(responseTime);
            log.info("Payment process completed for order #{}", paymentReqDto.getOrderId());
        }
    }


    private ResponseEntity<Void> paymentFallback(Throwable ex) {
        log.info("Payment Service is currently unavailable. Payment placement failed.");
        MetricsHelper.capturePaymentRequestFailureMetric();
        throw new GeneralException(GeneralErrorCode.PAYMENT_SERVICE_UNAVAILABLE);
    }

}
