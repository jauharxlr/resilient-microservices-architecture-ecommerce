package com.example.orderservice.service.impl;

import com.example.apps.paymentservice.model.PaymentReqDto;
import com.example.orderservice.constant.PaymentStatus;
import com.example.orderservice.dao.PaymentServiceDao;
import com.example.orderservice.model.dto.req.OrderReqDto;
import com.example.orderservice.model.dto.res.OrderResDto;
import com.example.orderservice.model.entity.OrderEntity;
import com.example.orderservice.repository.OrderEntityRepository;
import com.example.orderservice.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final PaymentServiceDao paymentServiceDao;
    private final OrderEntityRepository orderEntityRepository;

    @Override
    @Transactional
    public OrderResDto createOrder(OrderReqDto orderReqDto){
            log.info("OrderServiceImpl createOrder()");
            OrderEntity orderEntity = orderReqDto.toEntity();
            orderEntity = orderEntityRepository.save(orderEntity);
            log.debug("Order saved to db #{}", orderEntity.getId());
            processPayment(orderEntity, orderReqDto);
            log.info("Order processed successfully #{}", orderEntity.getId());
            return OrderResDto.builder().orderId(orderEntity.getId()).build();
    }

    @Override
    public List<OrderResDto> getOrders() {
        return orderEntityRepository.findAll().stream().map(OrderResDto::from).toList();
    }

    private void processPayment(OrderEntity orderEntity, OrderReqDto orderReqDto) {
        log.debug("Preparing payment request #{}", orderEntity.getId());
        PaymentReqDto paymentReqDto = PaymentReqDto.builder()
                .orderId(orderEntity.getId())
                .userId(orderReqDto.getUserId())
                .cardNo(orderReqDto.getPaymentDetails().getCardNo())
                .expiry(orderReqDto.getPaymentDetails().getExpiry())
                .cvv(orderReqDto.getPaymentDetails().getCvv())
                .payableAmount(orderEntity.getPayableAmount())
                .build();
        log.debug("Initiating payment request #{}", orderEntity.getId());
        try {
            paymentServiceDao.initiatePayment(paymentReqDto).get();
            orderEntity.setPaymentStatus(PaymentStatus.PAID);
            orderEntityRepository.save(orderEntity);
        } catch (InterruptedException | ExecutionException e) {
            log.debug("Payment request failed #{}", orderEntity.getId());
            orderEntity.setPaymentStatus(PaymentStatus.FAILED);
            orderEntityRepository.save(orderEntity);
            throw new RuntimeException(e);
        }
    }
}
