package com.example.notificationservice.helper;

import com.example.notificationservice.constant.MetricConstant;
import io.micrometer.core.instrument.Metrics;

public interface MetricsHelper {
    static void captureValidationErrorMetric(){
        Metrics.counter(MetricConstant.VALIDATION_ERROR.getMetric(), MetricConstant.VALIDATION_ERROR.getTags()).increment();
    }
    static void captureNotificationRequestMetric(){
        Metrics.counter(MetricConstant.NOTIFICATION_REQUEST.getMetric(), MetricConstant.NOTIFICATION_REQUEST.getTags()).increment();
    }
    static void captureNotificationRequestResponseTimeMetric(Long time){
        Metrics.gauge(MetricConstant.NOTIFICATION_REQUEST_RESPONSE_TIME.getMetric(), MetricConstant.NOTIFICATION_REQUEST_RESPONSE_TIME.getTagList(),time);
    }
}
