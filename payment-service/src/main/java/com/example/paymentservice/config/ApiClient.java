package com.example.paymentservice.config;

import com.example.apps.notificationservice.api.NotificationControllerApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ApiClient {

    private final RestTemplate restTemplate;
    private final ServiceNet serviceNet;

    public NotificationControllerApi getNotificationControllerApi(){
        return new NotificationControllerApi(getNotificationServiceApiClient());
    }

    private com.example.apps.notificationservice.ApiClient getNotificationServiceApiClient(){
        com.example.apps.notificationservice.ApiClient client = new com.example.apps.notificationservice.ApiClient(restTemplate);
        client.setBasePath(serviceNet.getNotificationService());
        return client;
    }
}
