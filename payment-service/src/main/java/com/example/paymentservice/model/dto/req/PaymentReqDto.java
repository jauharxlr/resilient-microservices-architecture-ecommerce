package com.example.paymentservice.model.dto.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentReqDto {
    @NotEmpty
    private String cardNo;
    @NotEmpty
    private String cvv;
    @NotEmpty
    private String expiry;
    @NotNull
    private Long userId;
    @NotNull
    private Long orderId;
    @NotNull
    private Double payableAmount;
}
