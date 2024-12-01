package com.example.orderservice.config;

import com.example.orderservice.constant.MetricConstants;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Slf4j
@Configuration
public class MetricsConfig {
    public void captureValidationErrorMetric(){
        Metrics.counter(MetricConstants.VALIDATION_ERROR.getMetric(), MetricConstants.VALIDATION_ERROR.getTags()).increment();
    }
    public void captureGeneralExceptionMetric(){
        Metrics.counter(MetricConstants.GENERAL_EXCEPTION.getMetric(), MetricConstants.GENERAL_EXCEPTION.getTags()).increment();
    }
    public void captureOrderRequestMetric(){
        Metrics.counter(MetricConstants.ORDER_REQUEST.getMetric(), MetricConstants.ORDER_REQUEST.getTags()).increment();
    }
    public void captureOrderRequestResponseTimeMetric(Long time){
        Metrics.gauge(MetricConstants.ORDER_REQUEST_RESPONSE_TIME.getMetric(), MetricConstants.ORDER_REQUEST_RESPONSE_TIME.getTagList(),time);
    }
}
