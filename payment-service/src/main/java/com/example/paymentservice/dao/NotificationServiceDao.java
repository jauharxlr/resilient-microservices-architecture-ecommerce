package com.example.paymentservice.dao;

import com.example.apps.notificationservice.model.NotificationReqDto;
import com.example.paymentservice.config.ApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceDao {

    private final ApiClient apiClient;

    public void sendNotification(NotificationReqDto notificationReqDto){
        try {
            log.info("Sending notification");
            apiClient.getNotificationControllerApi().sendNotification(notificationReqDto);
            log.debug("Notification sent successfully");
        } catch (RestClientException ex){
            log.error("Notification api triggered exception", ex);
        }
    }
}
