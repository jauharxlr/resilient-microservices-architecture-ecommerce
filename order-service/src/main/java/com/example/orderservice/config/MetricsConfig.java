package com.example.orderservice.config;

import com.example.orderservice.constant.MetricConstants;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicLong;

@Getter
@Slf4j
@Configuration
public class MetricsConfig {

    private final Counter orderRequestCounter;
    private final AtomicLong orderRequestResponseTimeGauge;

    public MetricsConfig(MeterRegistry meterRegistry){
        // Registering order counter
        orderRequestCounter = Counter.builder(MetricConstants.ORDER_REQUEST.getMetric())
                .description(MetricConstants.ORDER_REQUEST.getDescription())
                .tags(MetricConstants.ORDER_REQUEST.getTags())
                .register(meterRegistry);

        // Registering order response time gauge
        orderRequestResponseTimeGauge = new AtomicLong(0);
        Gauge.builder(MetricConstants.ORDER_REQUEST_RESPONSE_TIME.getMetric(), orderRequestResponseTimeGauge, AtomicLong::get)
                .description(MetricConstants.ORDER_REQUEST_RESPONSE_TIME.getDescription())
                .tags(MetricConstants.ORDER_REQUEST_RESPONSE_TIME.getTags())
                .register(meterRegistry);
    }

    public void setOrderRequestResponseTimeGauge(long responseTime) {
        orderRequestResponseTimeGauge.set(responseTime);
    }

}
