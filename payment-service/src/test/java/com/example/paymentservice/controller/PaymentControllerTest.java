package com.example.paymentservice.controller;

import com.example.paymentservice.advice.GlobalExceptionHandler;
import com.example.paymentservice.config.ServiceNet;
import com.example.paymentservice.model.dto.req.PaymentReqDto;
import com.example.paymentservice.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class PaymentControllerTest {

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private PaymentController paymentController;

    private MockMvc mockMvc;

    @MockBean
    private ServiceNet serviceNet;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController)
                .setControllerAdvice(globalExceptionHandler)
                .build();
    }

    @Test
    void testProcessPayment_Success() throws Exception {
        PaymentReqDto paymentReqDto = new PaymentReqDto();
        paymentReqDto.setOrderId(12345L);
        paymentReqDto.setPayableAmount(100.0);
        paymentReqDto.setUserId(1L);
        paymentReqDto.setCardNo("7623875278357283");
        paymentReqDto.setCvv("234");
        paymentReqDto.setExpiry("12/10");

        doNothing().when(paymentService).processPayment(paymentReqDto);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(paymentReqDto);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/payment")
                        .contentType("application/json")
                        .content(json))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        verify(paymentService, times(1)).processPayment(paymentReqDto);
    }


    @Test
    void testProcessPayment_CircuitBreaker_Opens() throws Exception {
        PaymentReqDto paymentReqDto = new PaymentReqDto();
        paymentReqDto.setOrderId(12345L);
        paymentReqDto.setPayableAmount(100.0);
        paymentReqDto.setUserId(1L);
        paymentReqDto.setCardNo("7623875278357283");
        paymentReqDto.setCvv("234");
        paymentReqDto.setExpiry("12/10");

        doThrow(new RuntimeException("Payment service unavailable"))
                .when(paymentService).processPayment(paymentReqDto);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(paymentReqDto);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/payment")
                        .contentType("application/json")
                        .content(json))
                .andReturn();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), mvcResult.getResponse().getStatus());
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Payment Service not available"));

        verify(paymentService, times(1)).processPayment(paymentReqDto);

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/payment")
                        .contentType("application/json")
                        .content(json))
                .andReturn();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), mvcResult.getResponse().getStatus());
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Payment Service not available"));

        verify(paymentService, times(2)).processPayment(paymentReqDto);
    }
}