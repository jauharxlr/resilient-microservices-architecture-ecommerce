package com.example.orderservice.service.impl;

import com.example.apps.paymentservice.model.PaymentReqDto;
import com.example.orderservice.dao.PaymentServiceDao;
import com.example.orderservice.model.dto.req.OrderReqDto;
import com.example.orderservice.model.dto.res.OrderResDto;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final PaymentServiceDao paymentServiceDao;

    @Override
    public OrderResDto createOrder(OrderReqDto orderReqDto){
        try{
            Long orderId = 1L;
            log.info("OrderServiceImpl createOrder()");
            processPayment(orderId, orderReqDto);
            return OrderResDto.builder().orderId(orderId).build();
        } finally {
            log.info("Order processed successfully");
        }
    }

    private void processPayment(Long orderId, OrderReqDto orderReqDto) {
        log.debug("Calculating payable amount");
        double payableAmount = orderReqDto.getCart().stream().mapToDouble(e->e.getPrice()*e.getQty()).sum();
        log.debug("Preparing payment request");
        PaymentReqDto paymentReqDto = PaymentReqDto.builder()
                .orderId(orderId)
                .userId(orderReqDto.getUserId())
                .cardNo(orderReqDto.getPaymentDetails().getCardNo())
                .expiry(orderReqDto.getPaymentDetails().getExpiry())
                .cvv(orderReqDto.getPaymentDetails().getCvv())
                .payableAmount(payableAmount)
                .build();
        log.debug("Initiating payment request");
        paymentServiceDao.initiatePayment(paymentReqDto);
    }
}
