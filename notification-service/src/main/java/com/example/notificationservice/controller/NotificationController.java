package com.example.notificationservice.controller;

import com.example.notificationservice.helper.MetricsHelper;
import com.example.notificationservice.model.dto.req.NotificationReqDto;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Post API to send a new notification")
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<Void> sendNotification(@RequestBody @Valid NotificationReqDto notificationReqDto) {
        log.info("NotificationController - sendNotification()");
        long startTime = System.currentTimeMillis();
        try {
            MetricsHelper.captureNotificationRequestMetric();
            log.info("Notification request processed for orderId #{}", notificationReqDto.getOrderId());
            return ResponseEntity.ok().build();
        } finally {
            long endTime = System.currentTimeMillis();
            long responseTime = endTime - startTime;
            MetricsHelper.captureNotificationRequestResponseTimeMetric(responseTime);
        }
    }
}
