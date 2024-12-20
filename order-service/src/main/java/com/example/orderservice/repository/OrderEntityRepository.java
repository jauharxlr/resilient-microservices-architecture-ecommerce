package com.example.orderservice.repository;

import com.example.orderservice.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderEntityRepository  extends JpaRepository<OrderEntity, Long> {
}
