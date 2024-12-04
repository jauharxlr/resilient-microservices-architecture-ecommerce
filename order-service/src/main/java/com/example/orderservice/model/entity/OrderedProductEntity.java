package com.example.orderservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
public class OrderedProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long productId;
    @Column(nullable = false)
    private Double quantity;
    @Column(nullable = false)
    private Double price;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderEntity orderEntity;
}
