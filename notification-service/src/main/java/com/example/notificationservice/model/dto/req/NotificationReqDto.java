package com.example.notificationservice.model.dto.req;

import com.example.notificationservice.constant.NotificationType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationReqDto {
    @NotNull
    private Long userId;
    @NotEmpty
    private NotificationType[] types;
    @NotEmpty
    private String message;
}
