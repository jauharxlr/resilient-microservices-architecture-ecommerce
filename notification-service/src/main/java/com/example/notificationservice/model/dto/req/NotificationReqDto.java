package com.example.notificationservice.model.dto.req;

import com.example.notificationservice.constant.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationReqDto {
    @NotNull
    @Schema(description = "Identifier of user", example = "12")
    private Long userId;
    @NotNull
    @Schema(description = "Identifier of order", example = "232")
    private Long orderId;
    @NotEmpty
    @Schema(description = "Type of notification", example = "PUSH")
    private NotificationType[] types;
    @NotEmpty
    @Schema(description = "Notification content", example = "detailed message")
    private String message;
}
