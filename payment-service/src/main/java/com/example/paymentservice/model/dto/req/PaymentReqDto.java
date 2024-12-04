package com.example.paymentservice.model.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentReqDto {
    @NotEmpty
    @Schema(description = "Customer credit/debit/prepaid card number", example = "1223456317452349")
    private String cardNo;
    @NotEmpty
    @Schema(description = "CVV of used card", example = "124")
    private String cvv;
    @NotEmpty
    @Schema(description = "Card expiry date in MM/YY", example = "12/26")
    private String expiry;
    @NotNull
    @Schema(description = "Identifier of user", example = "12")
    private Long userId;
    @NotNull
    @Schema(description = "Identifier of order", example = "125")
    private Long orderId;
    @NotNull
    @Schema(description = "Total payable amount", example = "1230.00")
    private Double payableAmount;
}
