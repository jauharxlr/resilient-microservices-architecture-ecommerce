package com.example.paymentservice.service.impl;

import com.example.paymentservice.config.ServiceNet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.apps.notificationservice.model.NotificationReqDto;
import com.example.paymentservice.constant.MessageConstant;
import com.example.paymentservice.dao.NotificationServiceDao;
import com.example.paymentservice.model.dto.req.PaymentReqDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PaymentServiceImplTest {

    @InjectMocks
    private PaymentServiceImpl paymentServiceImpl;

    @MockBean
    private ServiceNet serviceNet;

    @Mock
    private NotificationServiceDao notificationServiceDao;

    @Test
    void testProcessPayment_success() {
        PaymentReqDto paymentReqDto = new PaymentReqDto();
        paymentReqDto.setPayableAmount(100.0);
        paymentReqDto.setOrderId(12345L);
        paymentReqDto.setUserId(1L);
        CompletableFuture<Void> completableFuture = CompletableFuture.completedFuture(null);

        when(notificationServiceDao.sendNotification(any(NotificationReqDto.class))).thenReturn(completableFuture);
        paymentServiceImpl.processPayment(paymentReqDto);

        verify(notificationServiceDao, times(1)).sendNotification(any(NotificationReqDto.class));
    }

    @Test
    void testProcessPayment_failure() {
        PaymentReqDto paymentReqDto = new PaymentReqDto();
        paymentReqDto.setPayableAmount(100.0);
        paymentReqDto.setOrderId(12345L);
        paymentReqDto.setUserId(1L);

        CompletableFuture<Void> completableFuture = CompletableFuture.failedFuture(new RuntimeException("Notification failed"));
        when(notificationServiceDao.sendNotification(any(NotificationReqDto.class))).thenReturn(completableFuture);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> paymentServiceImpl.processPayment(paymentReqDto));
        assertTrue(exception.getMessage().contains("Notification failed"));

        verify(notificationServiceDao, times(1)).sendNotification(any(NotificationReqDto.class));
    }
}