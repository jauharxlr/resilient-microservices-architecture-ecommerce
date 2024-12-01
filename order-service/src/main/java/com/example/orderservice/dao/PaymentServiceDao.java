package com.example.orderservice.dao;

import com.example.apps.paymentservice.model.PaymentReqDto;
import com.example.orderservice.config.ApiClient;
import com.example.orderservice.helper.MetricsHelper;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceDao {

    private final ApiClient apiClient;

    @TimeLimiter(name = "paymentService", fallbackMethod = "paymentTimeoutFallback")
    @Retry(name = "paymentService", fallbackMethod = "paymentRetryFallback")
    public CompletableFuture<Void> initiatePayment(PaymentReqDto paymentReqDto) {
        MetricsHelper.capturePaymentRequestMetric();
        return CompletableFuture.runAsync(() -> {
            try {
                log.info("PaymentServiceDao initiatePayment()");
                apiClient.getPaymentControllerApi().processPayment(paymentReqDto);
                log.debug("Payment API call successful");
                MetricsHelper.capturePaymentSuccessMetric();
            } catch (RestClientException ex) {
                log.debug("Payment api triggered exception");
                throw ex;
            }
        });
    }

    private CompletableFuture<Void> paymentRetryFallback(Throwable ex) throws Throwable {
        log.info("Payment Service is currently unavailable. Retrying");
        MetricsHelper.capturePaymentRequestRetriedAndFailedMetric();
        throw ex;
    }

    private CompletableFuture<Void> paymentTimeoutFallback(Throwable ex) throws Throwable {
        log.info("Payment Service Timeout");
        MetricsHelper.capturePaymentRequestTimeoutMetric();
        throw ex;
    }

}
