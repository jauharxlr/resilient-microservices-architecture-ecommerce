package com.example.orderservice.service;

import com.example.orderservice.model.dto.req.OrderReqDto;
import com.example.orderservice.model.dto.res.OrderResDto;

public interface OrderService {
    OrderResDto createOrder(OrderReqDto orderReqDto);
}
