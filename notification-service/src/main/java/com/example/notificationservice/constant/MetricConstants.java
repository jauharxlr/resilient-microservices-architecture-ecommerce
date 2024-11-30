package com.example.notificationservice.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MetricConstants {

    NOTIFICATION_REQUEST("notification_request","Counter for tracking notification requests", new String[]{"service", "notification-service", "controller", "notificationController"}),
    NOTIFICATION_REQUEST_RESPONSE_TIME("notification_request_response_time","Guage for tracking notification requests response time", new String[]{"service", "notification-service", "controller", "notificationController"});
    private final String metric;
    private final String description;
    private final String[] tags;
}
