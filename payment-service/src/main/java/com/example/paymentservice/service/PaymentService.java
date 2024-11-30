package com.example.paymentservice.service;

import com.example.paymentservice.model.dto.req.PaymentReqDto;

public interface PaymentService {
    void processPayment(PaymentReqDto paymentReqDto);
}
