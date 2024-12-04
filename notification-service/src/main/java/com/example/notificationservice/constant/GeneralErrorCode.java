package com.example.notificationservice.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GeneralErrorCode {
    PAYMENT_SERVICE_UNAVAILABLE("ORD001","Payment Service not available!");
    final String code;
    final String description;
}
