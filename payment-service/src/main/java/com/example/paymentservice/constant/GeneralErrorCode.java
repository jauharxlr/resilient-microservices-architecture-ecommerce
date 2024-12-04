package com.example.paymentservice.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GeneralErrorCode {
    PAYMENT_SERVICE_UNAVAILABLE("PMT001","Payment Service not available!");
    final String code;
    final String description;
}
