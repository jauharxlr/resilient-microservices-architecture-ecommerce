package com.example.paymentservice.config;

import com.example.paymentservice.constant.MetricConstants;
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

    private final Counter paymentRequestCounter;
    private final AtomicLong paymentRequestResponseTimeGuage;

    public MetricsConfig(MeterRegistry meterRegistry){
        paymentRequestCounter = Counter.builder(MetricConstants.PAYMENT_REQUEST.getMetric())
                .description(MetricConstants.PAYMENT_REQUEST.getDescription())
                .tags(MetricConstants.PAYMENT_REQUEST.getTags())
                .register(meterRegistry);

        // Register the gauge
        paymentRequestResponseTimeGuage = new AtomicLong(0);
        Gauge.builder(MetricConstants.PAYMENT_REQUEST_RESPONSE_TIME.getMetric(), paymentRequestResponseTimeGuage, AtomicLong::get)
                .description(MetricConstants.PAYMENT_REQUEST_RESPONSE_TIME.getDescription())
                .tags(MetricConstants.PAYMENT_REQUEST_RESPONSE_TIME.getTags())
                .register(meterRegistry);
    }

    public void setPaymentRequestResponseTimeGuage(long responseTime) {
        paymentRequestResponseTimeGuage.set(responseTime);
    }

}
