package com.example.orderservice.constant;

import io.micrometer.core.instrument.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Getter
@RequiredArgsConstructor
public enum MetricConstants {

    VALIDATION_ERROR("validation_error","Counter for tracking validation errors", new String[]{"service", "order-service"}),
    ORDER_REQUEST("order_request","Counter for tracking order requests", new String[]{"service", "order-service", "controller", "OrderController"}),
    ORDER_REQUEST_RESPONSE_TIME("order_request_response_time","Gauge for tracking order requests response time", new String[]{"service", "order-service", "controller", "OrderController"}),
    GENERAL_EXCEPTION("general_exception","Counter for tracking General Exceptions", new String[]{"service", "order-service"});
    private final String metric;
    private final String description;
    private final String[] tags;
    public List<Tag> getTagList(){
        return IntStream.range(0, tags.length / 2)
                .mapToObj(i -> Tag.of(tags[i * 2], tags[i * 2 + 1]))
                .toList();
    }
}
