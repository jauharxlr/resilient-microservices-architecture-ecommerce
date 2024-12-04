package com.example.orderservice.helper;

import com.example.orderservice.constant.MetricConstant;
import io.micrometer.core.instrument.Metrics;

public interface MetricsHelper {
    static void captureValidationErrorMetric(){
        Metrics.counter(MetricConstant.VALIDATION_ERROR.getMetric(), MetricConstant.VALIDATION_ERROR.getTags()).increment();
    }
    static void captureGeneralExceptionMetric(){
        Metrics.counter(MetricConstant.GENERAL_EXCEPTION.getMetric(), MetricConstant.GENERAL_EXCEPTION.getTags()).increment();
    }
    static void captureOrderRequestMetric(){
        Metrics.counter(MetricConstant.ORDER_REQUEST.getMetric(), MetricConstant.ORDER_REQUEST.getTags()).increment();
    }
    static void captureGetOrderMetric(){
        Metrics.counter(MetricConstant.ORDER_GET_REQUEST.getMetric(), MetricConstant.ORDER_GET_REQUEST.getTags()).increment();
    }
    static void capturePaymentRequestMetric(){
        Metrics.counter(MetricConstant.PAYMENT_REQUEST.getMetric(), MetricConstant.PAYMENT_REQUEST.getTags()).increment();
    }
    static void capturePaymentRequestRetriedAndFailedMetric(){
        Metrics.counter(MetricConstant.PAYMENT_REQUEST_RETRY.getMetric(), MetricConstant.PAYMENT_REQUEST_RETRY.getTags()).increment();
    }
    static void capturePaymentRequestTimeoutMetric(){
        Metrics.counter(MetricConstant.PAYMENT_REQUEST_TIMEOUT.getMetric(), MetricConstant.PAYMENT_REQUEST_TIMEOUT.getTags()).increment();
    }
    static void capturePaymentSuccessMetric(){
        Metrics.counter(MetricConstant.PAYMENT_REQUEST_SUCCESS.getMetric(), MetricConstant.PAYMENT_REQUEST_SUCCESS.getTags()).increment();
    }
    static void captureOrderFailureMetric(){
        Metrics.counter(MetricConstant.ORDER_FAILURE.getMetric(), MetricConstant.ORDER_FAILURE.getTags()).increment();
    }
    static void captureOrderSuccessMetric(){
        Metrics.counter(MetricConstant.ORDER_SUCCESS.getMetric(), MetricConstant.ORDER_SUCCESS.getTags()).increment();
    }
    static void captureOrderRequestResponseTimeMetric(Long time){
        Metrics.gauge(MetricConstant.ORDER_REQUEST_RESPONSE_TIME.getMetric(), MetricConstant.ORDER_REQUEST_RESPONSE_TIME.getTagList(),time);
    }
    static void captureGetOrderRequestResponseTimeMetric(Long time){
        Metrics.gauge(MetricConstant.ORDER_GET_REQUEST_RESPONSE_TIME.getMetric(), MetricConstant.ORDER_GET_REQUEST_RESPONSE_TIME.getTagList(),time);
    }
}
