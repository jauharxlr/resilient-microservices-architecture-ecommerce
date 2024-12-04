package com.example.paymentservice.service.impl;

import com.example.apps.notificationservice.model.NotificationReqDto;
import com.example.paymentservice.constant.MessageConstant;
import com.example.paymentservice.dao.NotificationServiceDao;
import com.example.paymentservice.model.dto.req.PaymentReqDto;
import com.example.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final NotificationServiceDao notificationServiceDao;

    @Override
    public void processPayment(PaymentReqDto paymentReqDto) {
        log.debug("Preparing request for notification");
        String paymentMessage = MessageConstant.PAYMENT_MESSAGE
                .replace(MessageConstant.PAYMENT_MESSAGE_AMOUNT_PLACEHOLDER, String.valueOf(paymentReqDto.getPayableAmount()))
                .replace(MessageConstant.PAYMENT_MESSAGE_ORDER_ID_PLACEHOLDER, String.valueOf(paymentReqDto.getOrderId()));
        NotificationReqDto notificationReqDto = NotificationReqDto.builder()
                .message(paymentMessage)
                .userId(paymentReqDto.getUserId())
                .orderId(paymentReqDto.getOrderId())
                .types(List.of(NotificationReqDto.TypesEnum.PUSH))
                .build();
        try {
            notificationServiceDao.sendNotification(notificationReqDto).get();
            log.debug("Notification sent for order #{}", paymentReqDto.getOrderId());
        } catch (InterruptedException | ExecutionException e) {
            log.debug("Notification request failed #{}", paymentReqDto.getOrderId());
            throw new RuntimeException(e);
        }
    }
}
