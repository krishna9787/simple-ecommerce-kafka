package com.krishna9787.order_service.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import com.krishna9787.order_service.dto.OrdersDto;
import com.krishna9787.order_service.entity.Orders;
import com.krishna9787.order_service.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private KafkaTemplate<String, OrdersDto> kafkaTemplate;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void shouldReturnOrderWhenOrderExists() {
        Orders order = new Orders();
        order.setOrderId("ORD-1001");

        when(orderRepository.findByOrderId("ORD-1001"))
                .thenReturn(Optional.of(order));

        OrdersDto result = orderService.findOrderById("ORD-1001");

        assertEquals("ORD-1001", result.getOrderId());
    }

    @Test
    void shouldThrowExceptionWhenOrderDoesNotExist() {
        when(orderRepository.findByOrderId("ORD-1001"))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.findOrderById("ORD-1001"));

        assertEquals("Order not found", exception.getMessage());
    }

    @Test
    void shouldFindOrderById() {
        Orders order = new Orders();
        order.setOrderId("ORD-1001");

        when(orderRepository.findByOrderId("ORD-1001"))
                .thenReturn(Optional.of(order));

        orderService.findOrderById("ORD-1001");
        verify(orderRepository).findByOrderId("ORD-1001");
    }
}
