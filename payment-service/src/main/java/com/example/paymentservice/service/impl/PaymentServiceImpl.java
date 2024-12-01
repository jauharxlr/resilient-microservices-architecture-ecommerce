package com.example.paymentservice.service.impl;

import com.example.apps.notificationservice.model.NotificationReqDto;
import com.example.paymentservice.constant.MessageConstants;
import com.example.paymentservice.dao.NotificationServiceDao;
import com.example.paymentservice.model.dto.req.PaymentReqDto;
import com.example.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final NotificationServiceDao notificationServiceDao;

    @Override
    public void processPayment(PaymentReqDto paymentReqDto) {
        log.info("Payment completed successfully");
        log.debug("Preparing request for notification");
        String paymentMessage = MessageConstants.PAYMENT_MESSAGE
                .replace(MessageConstants.PAYMENT_MESSAGE_AMOUNT_PLACEHOLDER, String.valueOf(paymentReqDto.getPayableAmount()))
                .replace(MessageConstants.PAYMENT_MESSAGE_ORDER_ID_PLACEHOLDER, String.valueOf(paymentReqDto.getOrderId()));
        NotificationReqDto notificationReqDto = NotificationReqDto.builder()
                .message(paymentMessage)
                .userId(paymentReqDto.getUserId())
                .types(List.of(NotificationReqDto.TypesEnum.PUSH))
                .build();
        notificationServiceDao.sendNotification(notificationReqDto);
    }
}
