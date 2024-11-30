package com.example.orderservice.model.dto.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderReqDto {

    @NotNull
    private Long userId;
    @NotEmpty
    private List<Cart> cart;
    @NotNull
    private Payment paymentDetails;

    @Data
    public static class Cart {
        @NotNull
        private Long productId;
        @NotNull
        private Double price;
        @NotNull
        private Double qty;
    }

    @Data
    public static class Payment {
        @NotEmpty
        private String cardNo;
        @NotEmpty
        private String cvv;
        @NotEmpty
        private String expiry;
    }
}
