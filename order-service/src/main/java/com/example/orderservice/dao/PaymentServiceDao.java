package com.example.orderservice.dao;

import com.example.apps.paymentservice.model.PaymentReqDto;
import com.example.orderservice.config.ApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceDao {
    private final ApiClient apiClient;

    public void initiatePayment(PaymentReqDto paymentReqDto) {
        try {
            log.info("PaymentServiceDao initiatePayment()");
            apiClient.getPaymentControllerApi().processPayment(paymentReqDto);
            log.debug("Payment processed successfully");
        } catch (RestClientException ex) {
            log.error("Payment api triggered exception", ex);
        }
    }
}
