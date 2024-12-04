package com.example.orderservice.constant;

import io.micrometer.core.instrument.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.IntStream;

@Getter
@RequiredArgsConstructor
public enum MetricConstant {

    VALIDATION_ERROR("validation_error", "Counter for tracking validation errors", new String[]{"service", "order-service"}),
    ORDER_REQUEST("order_request", "Counter for tracking order requests", new String[]{"service", "order-service", "controller", "OrderController"}),
    ORDER_REQUEST_RESPONSE_TIME("order_request_response_time", "Gauge for tracking order requests response time", new String[]{"service", "order-service", "controller", "OrderController"}),
    GENERAL_EXCEPTION("general_exception", "Counter for tracking General Exceptions", new String[]{"service", "order-service"}),
    PAYMENT_REQUEST("payment_request", "Counter for tracking outgoing payment API call", new String[]{"service", "order-service"}),
    PAYMENT_REQUEST_RETRY("payment_request_retry", "Counter for tracking outgoing payment API call try failures", new String[]{"service", "order-service"}),
    PAYMENT_REQUEST_TIMEOUT("payment_request_timeout", "Counter for tracking outgoing payment API call timeout failures", new String[]{"service", "order-service"}),
    PAYMENT_REQUEST_SUCCESS("payment_request_success", "Counter for tracking outgoing payment API call with success response", new String[]{"service", "order-service"}),
    ORDER_FAILURE("order_request_failure", "Counter for tracking order request failure", new String[]{"service", "order-service"}),
    ORDER_SUCCESS("order_request_success", "Counter for tracking order request success", new String[]{"service", "order-service"}),
    ORDER_GET_REQUEST("order_get_request", "Counter for tracking order get requests", new String[]{"service", "order-service", "controller", "OrderController"}),
    ORDER_GET_REQUEST_RESPONSE_TIME("order_get_request_response_time", "Gauge for tracking order get requests response time", new String[]{"service", "order-service", "controller", "OrderController"});

    private final String metric;
    private final String description;
    private final String[] tags;

    public List<Tag> getTagList() {
        return IntStream.range(0, tags.length / 2)
                .mapToObj(i -> Tag.of(tags[i * 2], tags[i * 2 + 1]))
                .toList();
    }
}
