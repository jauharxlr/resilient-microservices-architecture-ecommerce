package com.example.orderservice.model.dto.res;

import com.example.orderservice.constant.PaymentStatus;
import com.example.orderservice.model.entity.OrderEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResDto {
    Long orderId;
    PaymentStatus paymentStatus;

    public static OrderResDto from(OrderEntity orderEntity){
        return OrderResDto.builder()
                .orderId(orderEntity.getId())
                .paymentStatus(orderEntity.getPaymentStatus())
                .build();
    }
}
