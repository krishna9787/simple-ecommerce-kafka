package com.krishna9787.order_service.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import com.krishna9787.order_service.dto.InventoryReservedDto;
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
        order.setCustomerId("CUST-1");

        when(orderRepository.findByOrderId("ORD-1001"))
                .thenReturn(Optional.of(order));

        OrdersDto result = orderService.findOrderById("ORD-1001");

        assertEquals("ORD-1001", result.getOrderId());
        assertEquals("CUST-1", result.getCustomerId());
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

    @Test
    void shouldSaveOrderAndPublishKafkaMessage() {
        OrdersDto orderDto = new OrdersDto();
        orderDto.setOrderId("ORD-2002");
        orderDto.setCustomerId("CUST-2");
        orderDto.setProductId("PROD-2");
        orderDto.setQuantity(3);
        orderDto.setAmount(new BigDecimal("99.99"));

        Orders savedOrder = new Orders();
        savedOrder.setOrderId("ORD-2002");
        savedOrder.setCustomerId("CUST-2");
        savedOrder.setProductId("PROD-2");
        savedOrder.setQuantity(3);
        savedOrder.setAmount(new BigDecimal("99.99"));

        when(orderRepository.save(any(Orders.class))).thenReturn(savedOrder);

        SendResult<String, OrdersDto> sendResult = org.mockito.Mockito.mock(SendResult.class);
        RecordMetadata recordMetadata = org.mockito.Mockito.mock(RecordMetadata.class);
        when(sendResult.getRecordMetadata()).thenReturn(recordMetadata);
        when(recordMetadata.partition()).thenReturn(0);
        when(recordMetadata.offset()).thenReturn(42L);
        when(kafkaTemplate.send(eq(OrderServiceImpl.ORDER_CREATED_TOPIC), eq("ORD-2002"), any(OrdersDto.class)))
                .thenReturn(CompletableFuture.completedFuture(sendResult));

        orderService.addOrder(orderDto);

        ArgumentCaptor<Orders> ordersCaptor = ArgumentCaptor.forClass(Orders.class);
        verify(orderRepository).save(ordersCaptor.capture());
        assertEquals("ORD-2002", ordersCaptor.getValue().getOrderId());
        verify(kafkaTemplate).send(eq(OrderServiceImpl.ORDER_CREATED_TOPIC), eq("ORD-2002"), any(OrdersDto.class));
    }

    @Test
    void shouldUpdateEventTypeWhenInventoryReserved() {
        InventoryReservedDto inventoryReservedDto = new InventoryReservedDto("ORD-3003", "RESERVED");
        Orders order = new Orders();
        order.setOrderId("ORD-3003");
        order.setEventType("PENDING");

        when(orderRepository.findByOrderId("ORD-3003")).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Orders.class))).thenReturn(order);

        orderService.handleReservedINventory(inventoryReservedDto);

        assertEquals("RESERVED", order.getEventType());
        verify(orderRepository).save(order);
    }

    @Test
    void shouldThrowExceptionWhenInventoryReservedOrderDoesNotExist() {
        when(orderRepository.findByOrderId("ORD-404")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> orderService.handleReservedINventory(new InventoryReservedDto("ORD-404", "RESERVED")));

        assertEquals("order not found", exception.getMessage());
    }
}
