package com.krishna9787.order_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishna9787.order_service.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findByOrderId(String orderId);
}
