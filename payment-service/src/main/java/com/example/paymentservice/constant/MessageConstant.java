package com.example.paymentservice.constant;

public interface MessageConstant {
    String PAYMENT_MESSAGE_AMOUNT_PLACEHOLDER = "{{amount}}";
    String PAYMENT_MESSAGE_ORDER_ID_PLACEHOLDER = "{{orderId}}";
    String PAYMENT_MESSAGE = "Dear customer, your payment of "+PAYMENT_MESSAGE_AMOUNT_PLACEHOLDER+" AED has been received against order #"+PAYMENT_MESSAGE_ORDER_ID_PLACEHOLDER+" successfully.";
}
