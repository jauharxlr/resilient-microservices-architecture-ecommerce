package com.example.paymentservice.service.impl;

import com.example.paymentservice.dao.NotificationServiceDao;
import com.example.paymentservice.model.dto.req.PaymentReqDto;
import com.example.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final NotificationServiceDao notificationServiceDao;

    @Override
    public void processPayment(PaymentReqDto paymentReqDto) {
        log.info("Payment completed successfully");
        notificationServiceDao.sendNotification();
    }
}
