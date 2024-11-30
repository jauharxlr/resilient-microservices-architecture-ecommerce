package com.example.orderservice.config;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "service-net")
public class ServiceNet {

    @NotEmpty
    private String paymentService;

}
