package com.example.orderservice.model.dto.req;

import com.example.orderservice.constant.PaymentStatus;
import com.example.orderservice.model.entity.OrderEntity;
import com.example.orderservice.model.entity.OrderedProductEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Schema(description = "Request payload for creating an order")
public class OrderReqDto {

    @NotNull
    @Schema(description = "Identifier of user", example = "12")
    private Long userId;
    @NotEmpty
    @Schema(description = "An array of items in user's cart")
    private List<Cart> cart;
    @NotNull
    @Schema(description = "Customer card details for payment")
    private Payment paymentDetails;

    @Data
    public static class Cart {
        @NotNull
        @Schema(description = "Identifier of product", example = "17")
        private Long productId;
        @NotNull
        @Schema(description = "Selling price of the product", example = "12.5")
        private Double price;
        @NotNull
        @Schema(description = "Quantity of product", example = "5")
        private Double qty;

        public OrderedProductEntity toEntity() {
            return OrderedProductEntity.builder()
                    .quantity(qty)
                    .productId(productId)
                    .price(price)
                    .build();
        }
    }

    @Data
    public static class Payment {
        @NotEmpty
        @Schema(description = "The credit/debit/prepaid card number", example = "1265456387142645")
        private String cardNo;
        @NotEmpty
        @Schema(description = "CVV of the card", example = "123")
        private String cvv;
        @NotEmpty
        @Schema(description = "Expiry date of the card", example = "16/25")
        private String expiry;
    }

    public OrderEntity toEntity() {
        double payableAmount = cart.stream().mapToDouble(e -> e.getPrice() * e.getQty()).sum();
        List<OrderedProductEntity> orderedProducts = cart.stream().map(OrderReqDto.Cart::toEntity).collect(Collectors.toList());
        return OrderEntity.builder()
                .payableAmount(payableAmount)
                .userId(userId)
                .orderedProducts(orderedProducts)
                .paymentStatus(PaymentStatus.PENDING)
                .build();
    }
}
