package com.example.paymentservice.dao;

import com.example.apps.notificationservice.model.NotificationReqDto;
import com.example.paymentservice.config.ApiClient;
import com.example.paymentservice.helper.MetricsHelper;
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
public class NotificationServiceDao {

    private final ApiClient apiClient;

    @TimeLimiter(name = "notificationService", fallbackMethod = "notificationTimeoutFallback")
    @Retry(name = "notificationService", fallbackMethod = "notificationRetryFallback")
    public CompletableFuture<Void> sendNotification(NotificationReqDto notificationReqDto) {
        MetricsHelper.captureNotificationRequestMetric();
        return CompletableFuture.runAsync(() -> {
            try {
                log.info("Sending notification");
                apiClient.getNotificationControllerApi().sendNotification(notificationReqDto);
                log.debug("Notification sent successfully");
            } catch (RestClientException ex) {
                log.debug("Notification api triggered exception");
                throw ex;
            }
        });
    }

    private CompletableFuture<Void> notificationRetryFallback(Throwable ex) throws Throwable {
        log.info("Notification Service is currently unavailable. Retrying");
        MetricsHelper.captureNotificationRequestRetryMetric();
        throw ex;
    }

    private CompletableFuture<Void> notificationTimeoutFallback(Throwable ex) throws Throwable {
        log.info("Notification Service Timeout");
        MetricsHelper.captureNotificationRequestTimeoutMetric();
        throw ex;
    }
}
