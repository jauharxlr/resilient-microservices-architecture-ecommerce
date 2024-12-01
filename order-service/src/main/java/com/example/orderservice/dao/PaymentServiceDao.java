package com.example.orderservice.dao;

import com.example.apps.paymentservice.model.PaymentReqDto;
import com.example.orderservice.config.ApiClient;
import com.example.orderservice.constant.GeneralErrorCode;
import com.example.orderservice.exception.GeneralException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceDao {
    private final ApiClient apiClient;

    //    @TimeLimiter(name = "paymentService")
    @Retry(name = "paymentService", fallbackMethod = "retryFallback")
    public void initiatePayment(PaymentReqDto paymentReqDto) {
        try {
            log.info("PaymentServiceDao initiatePayment()");
            apiClient.getPaymentControllerApi().processPayment(paymentReqDto);
            log.debug("Payment API call successful");
        } catch (RestClientException ex) {
            log.debug("Payment api triggered exception");
            throw ex;
        }
    }

    private void retryFallback(Throwable ex) throws Throwable {
        log.info("Payment Service is currently unavailable. Retrying");
        throw ex;
    }

}
