package com.example.paymentservice.constant;

import io.micrometer.core.instrument.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.IntStream;

@Getter
@RequiredArgsConstructor
public enum MetricConstant {

    VALIDATION_ERROR("validation_error", "Counter for tracking validation errors", new String[]{"service", "order-service"}),
    GENERAL_EXCEPTION("general_exception", "Counter for tracking General Exceptions", new String[]{"service", "order-service"}),

    PAYMENT_REQUEST("payment_request","Counter for tracking payment requests", new String[]{"service", "payment-service", "controller", "paymentController"}),
    PAYMENT_REQUEST_RESPONSE_TIME("payment_request_response_time","Gauge for tracking payment requests response time", new String[]{"service", "payment-service", "controller", "paymentController"}),
    PAYMENT_REQUEST_SUCCESS("payment_request_success","Counter for tracking payment request success", new String[]{"service", "payment-service"}),
    PAYMENT_REQUEST_FAILURE("payment_request_failure","Counter for tracking payment request failure", new String[]{"service", "payment-service"}),

    NOTIFICATION_REQUEST("notification_request", "Counter for tracking outgoing notification API call", new String[]{"service", "payment-service"}),
    NOTIFICATION_REQUEST_RETRY("notification_request_retry", "Counter for tracking outgoing notification API call try failures", new String[]{"service", "payment-service"}),
    NOTIFICATION_REQUEST_TIMEOUT("notification_request_timeout", "Counter for tracking outgoing notification API call timeout failures", new String[]{"service", "payment-service"}),
    NOTIFICATION_REQUEST_SUCCESS("notification_request_success", "Counter for tracking outgoing notification API call with success response", new String[]{"service", "payment-service"});



    private final String metric;
    private final String description;
    private final String[] tags;

    public List<Tag> getTagList() {
        return IntStream.range(0, tags.length / 2)
                .mapToObj(i -> Tag.of(tags[i * 2], tags[i * 2 + 1]))
                .toList();
    }
}
