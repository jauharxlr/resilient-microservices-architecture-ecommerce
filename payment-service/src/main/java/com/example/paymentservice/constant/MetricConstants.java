package com.example.paymentservice.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MetricConstants {

    PAYMENT_REQUEST("payment_request","Counter for tracking payment requests", new String[]{"service", "payment-service", "controller", "paymentController"}),
    PAYMENT_REQUEST_RESPONSE_TIME("payment_request_response_time","Guage for tracking payment requests response time", new String[]{"service", "payment-service", "controller", "paymentController"});
    private final String metric;
    private final String description;
    private final String[] tags;
}
