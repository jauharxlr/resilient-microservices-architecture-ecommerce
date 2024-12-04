package com.example.paymentservice.helper;

import com.example.paymentservice.constant.MetricConstant;
import io.micrometer.core.instrument.Metrics;

public interface MetricsHelper {
    static void captureValidationErrorMetric(){
        Metrics.counter(MetricConstant.VALIDATION_ERROR.getMetric(), MetricConstant.VALIDATION_ERROR.getTags()).increment();
    }
    static void captureGeneralExceptionMetric(){
        Metrics.counter(MetricConstant.GENERAL_EXCEPTION.getMetric(), MetricConstant.GENERAL_EXCEPTION.getTags()).increment();
    }
    static void capturePaymentRequestMetric(){
        Metrics.counter(MetricConstant.PAYMENT_REQUEST.getMetric(), MetricConstant.PAYMENT_REQUEST.getTags()).increment();
    }
    static void capturePaymentRequestSuccessMetric(){
        Metrics.counter(MetricConstant.PAYMENT_REQUEST_SUCCESS.getMetric(), MetricConstant.PAYMENT_REQUEST_SUCCESS.getTags()).increment();
    }
    static void capturePaymentRequestFailureMetric(){
        Metrics.counter(MetricConstant.PAYMENT_REQUEST_FAILURE.getMetric(), MetricConstant.PAYMENT_REQUEST_FAILURE.getTags()).increment();
    }
    static void captureGetOrderRequestResponseTimeMetric(Long time){
        Metrics.gauge(MetricConstant.PAYMENT_REQUEST_RESPONSE_TIME.getMetric(), MetricConstant.PAYMENT_REQUEST_RESPONSE_TIME.getTagList(),time);
    }

    static void captureNotificationRequestMetric(){
        Metrics.counter(MetricConstant.NOTIFICATION_REQUEST.getMetric(), MetricConstant.NOTIFICATION_REQUEST.getTags()).increment();
    }
    static void captureNotificationRequestRetryMetric(){
        Metrics.counter(MetricConstant.NOTIFICATION_REQUEST_RETRY.getMetric(), MetricConstant.NOTIFICATION_REQUEST_RETRY.getTags()).increment();
    }
    static void captureNotificationRequestSuccessMetric(){
        Metrics.counter(MetricConstant.NOTIFICATION_REQUEST_SUCCESS.getMetric(), MetricConstant.NOTIFICATION_REQUEST_SUCCESS.getTags()).increment();
    }
    static void captureNotificationRequestTimeoutMetric(){
        Metrics.counter(MetricConstant.NOTIFICATION_REQUEST_TIMEOUT.getMetric(), MetricConstant.NOTIFICATION_REQUEST_TIMEOUT.getTags()).increment();
    }
}
