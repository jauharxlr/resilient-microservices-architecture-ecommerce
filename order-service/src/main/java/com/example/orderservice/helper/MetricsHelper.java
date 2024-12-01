package com.example.orderservice.helper;

import com.example.orderservice.constant.MetricConstants;
import io.micrometer.core.instrument.Metrics;

public interface MetricsHelper {
    static void captureValidationErrorMetric(){
        Metrics.counter(MetricConstants.VALIDATION_ERROR.getMetric(), MetricConstants.VALIDATION_ERROR.getTags()).increment();
    }
    static void captureGeneralExceptionMetric(){
        Metrics.counter(MetricConstants.GENERAL_EXCEPTION.getMetric(), MetricConstants.GENERAL_EXCEPTION.getTags()).increment();
    }
    static void captureOrderRequestMetric(){
        Metrics.counter(MetricConstants.ORDER_REQUEST.getMetric(), MetricConstants.ORDER_REQUEST.getTags()).increment();
    }
    static void capturePaymentRequestMetric(){
        Metrics.counter(MetricConstants.PAYMENT_REQUEST.getMetric(), MetricConstants.PAYMENT_REQUEST.getTags()).increment();
    }
    static void capturePaymentRequestRetriedAndFailedMetric(){
        Metrics.counter(MetricConstants.PAYMENT_REQUEST_RETRY.getMetric(), MetricConstants.PAYMENT_REQUEST_RETRY.getTags()).increment();
    }
    static void capturePaymentRequestTimeoutMetric(){
        Metrics.counter(MetricConstants.PAYMENT_REQUEST_TIMEOUT.getMetric(), MetricConstants.PAYMENT_REQUEST_TIMEOUT.getTags()).increment();
    }
    static void capturePaymentSuccessMetric(){
        Metrics.counter(MetricConstants.PAYMENT_REQUEST_SUCCESS.getMetric(), MetricConstants.PAYMENT_REQUEST_SUCCESS.getTags()).increment();
    }
    static void captureOrderFailureMetric(){
        Metrics.counter(MetricConstants.ORDER_FAILURE.getMetric(), MetricConstants.ORDER_FAILURE.getTags()).increment();
    }
    static void captureOrderSuccessMetric(){
        Metrics.counter(MetricConstants.ORDER_SUCCESS.getMetric(), MetricConstants.ORDER_SUCCESS.getTags()).increment();
    }
    static void captureOrderRequestResponseTimeMetric(Long time){
        Metrics.gauge(MetricConstants.ORDER_REQUEST_RESPONSE_TIME.getMetric(), MetricConstants.ORDER_REQUEST_RESPONSE_TIME.getTagList(),time);
    }
}
