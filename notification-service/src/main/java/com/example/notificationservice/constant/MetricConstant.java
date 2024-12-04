package com.example.notificationservice.constant;

import io.micrometer.core.instrument.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.IntStream;

@Getter
@RequiredArgsConstructor
public enum MetricConstant {

    NOTIFICATION_REQUEST("notification_request","Counter for tracking notification requests", new String[]{"service", "notification-service", "controller", "notificationController"}),
    NOTIFICATION_REQUEST_RESPONSE_TIME("notification_request_response_time","Guage for tracking notification requests response time", new String[]{"service", "notification-service", "controller", "notificationController"}),
    VALIDATION_ERROR("validation_error", "Counter for tracking validation errors", new String[]{"service", "notification-service"});

    private final String metric;
    private final String description;
    private final String[] tags;

    public List<Tag> getTagList() {
        return IntStream.range(0, tags.length / 2)
                .mapToObj(i -> Tag.of(tags[i * 2], tags[i * 2 + 1]))
                .toList();
    }
}
