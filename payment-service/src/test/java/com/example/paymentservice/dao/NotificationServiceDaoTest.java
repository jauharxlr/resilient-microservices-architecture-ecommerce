package com.example.paymentservice.dao;

import com.example.apps.notificationservice.api.NotificationControllerApi;
import com.example.apps.notificationservice.model.NotificationReqDto;
import com.example.paymentservice.config.ApiClient;
import com.example.paymentservice.config.ServiceNet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestClientException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class NotificationServiceDaoTest {

    @Autowired
    private NotificationServiceDao notificationServiceDao;

    @MockBean
    private ApiClient apiClient;

    @MockBean
    private ServiceNet serviceNet;

    @Test
    void testRetryMechanism() {
        NotificationReqDto notificationReqDto = new NotificationReqDto();
        NotificationControllerApi notificationControllerApi = mock(NotificationControllerApi.class);

        when(apiClient.getNotificationControllerApi()).thenReturn(notificationControllerApi);
        doThrow(new RestClientException("Service unavailable"))
                .when(notificationControllerApi).sendNotification(any(NotificationReqDto.class));

        CompletableFuture<Void> future = notificationServiceDao.sendNotification(notificationReqDto);

        assertThrows(ExecutionException.class, future::get);
        verify(notificationControllerApi, times(3)).sendNotification(any(NotificationReqDto.class));
    }
}
