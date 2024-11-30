package com.example.notificationservice.controller;

import com.example.notificationservice.model.dto.req.NotificationReqDto;
import com.example.notificationservice.config.MetricsConfig;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/notify")
@RequiredArgsConstructor
public class NotificationController {

    private final MetricsConfig metricsConfig;

    @PostMapping
    public ResponseEntity<Void> sendNotification(@RequestBody @Valid NotificationReqDto notificationReqDto) {
        log.info("NotificationController - sendNotification()");
        long startTime = System.currentTimeMillis();
        try {
            metricsConfig.getNotificationRequestCounter().increment();
            log.info("Notification request processed {}", notificationReqDto);
            return ResponseEntity.ok().build();
        } finally {
            long endTime = System.currentTimeMillis();
            long responseTime = endTime - startTime;
            metricsConfig.setNotificationRequestResponseTimeGuage(responseTime);
        }
    }
}
