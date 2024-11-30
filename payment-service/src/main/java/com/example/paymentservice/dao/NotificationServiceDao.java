package com.example.paymentservice.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceDao {

    public void sendNotification(){
        log.info("sending notification");
    }
}
