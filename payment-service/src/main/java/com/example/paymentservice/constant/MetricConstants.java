package com.example.paymentservice.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MetricConstants {

    ORDER_REQUEST("order_request","Counter for tracking order requests", new String[]{"service", "order-service", "controller", "OrderController"}),
    ORDER_REQUEST_RESPONSE_TIME("order_request_response_time","Guage for tracking order requests response time", new String[]{"service", "order-service", "controller", "OrderController"});
    private final String metric;
    private final String description;
    private final String[] tags;
}
