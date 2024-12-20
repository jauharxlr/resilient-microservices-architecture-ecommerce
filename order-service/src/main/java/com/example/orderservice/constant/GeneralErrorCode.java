package com.example.orderservice.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GeneralErrorCode {
    ORDER_SERVICE_UNAVAILABLE("ORD001","Order Service not available!");
    final String code;
    final String description;
}
