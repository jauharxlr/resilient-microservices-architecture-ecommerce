package com.example.orderservice.config;

import com.example.apps.paymentservice.api.PaymentControllerApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ApiClient {

    private final RestTemplate restTemplate;
    private final ServiceNet serviceNet;

    public PaymentControllerApi getPaymentControllerApi(){
        return new PaymentControllerApi(getPaymentServiceApiClient());
    }

    private com.example.apps.paymentservice.ApiClient getPaymentServiceApiClient(){
        com.example.apps.paymentservice.ApiClient client = new com.example.apps.paymentservice.ApiClient(restTemplate);
        client.setBasePath(serviceNet.getPaymentService());
        return client;
    }
}
