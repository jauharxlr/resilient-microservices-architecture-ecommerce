package com.example.paymentservice.controller;

import com.example.paymentservice.config.MetricsConfig;
import com.example.paymentservice.model.dto.req.PaymentReqDto;
import com.example.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final MetricsConfig metricsConfig;
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Void> processPayment(@RequestBody @Valid PaymentReqDto paymentReqDto) {
        log.info("PaymentController - processPayment()");
        long startTime = System.currentTimeMillis();
        try {
            metricsConfig.getPaymentRequestCounter().increment();
            paymentService.processPayment(paymentReqDto);
            return ResponseEntity.ok().build();
        } finally {
            long endTime = System.currentTimeMillis();
            long responseTime = endTime - startTime;
            metricsConfig.setPaymentRequestResponseTimeGuage(responseTime);
            log.info("Payment process completed for order #{}", paymentReqDto.getOrderId());
        }
    }
}
