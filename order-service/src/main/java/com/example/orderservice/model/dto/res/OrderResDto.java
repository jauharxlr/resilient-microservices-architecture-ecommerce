package com.example.orderservice.model.dto.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResDto {
    Long orderId;
    String status;
    String message;
}
