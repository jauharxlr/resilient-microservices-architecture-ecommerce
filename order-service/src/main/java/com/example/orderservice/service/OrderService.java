package com.example.orderservice.service;

import com.example.orderservice.model.dto.req.OrderReqDto;
import com.example.orderservice.model.dto.res.OrderResDto;

import java.util.List;

public interface OrderService {
    OrderResDto createOrder(OrderReqDto orderReqDto);

    List<OrderResDto> getOrders();
}
