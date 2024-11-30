package com.example.notificationservice.config;

import com.example.notificationservice.constant.MetricConstants;
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

    private final Counter notificationRequestCounter;
    private final AtomicLong notificationRequestResponseTimeGuage;

    public MetricsConfig(MeterRegistry meterRegistry){
        notificationRequestCounter = Counter.builder(MetricConstants.NOTIFICATION_REQUEST.getMetric())
                .description(MetricConstants.NOTIFICATION_REQUEST.getDescription())
                .tags(MetricConstants.NOTIFICATION_REQUEST.getTags())
                .register(meterRegistry);

        // Register the gauge
        notificationRequestResponseTimeGuage = new AtomicLong(0);
        Gauge.builder(MetricConstants.NOTIFICATION_REQUEST_RESPONSE_TIME.getMetric(), notificationRequestResponseTimeGuage, AtomicLong::get)
                .description(MetricConstants.NOTIFICATION_REQUEST_RESPONSE_TIME.getDescription())
                .tags(MetricConstants.NOTIFICATION_REQUEST_RESPONSE_TIME.getTags())
                .register(meterRegistry);
    }

    public void setNotificationRequestResponseTimeGuage(long responseTime) {
        notificationRequestResponseTimeGuage.set(responseTime);
    }

}
