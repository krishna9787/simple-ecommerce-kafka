package com.krishna9787.order_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.krishna9787.order_service.dto.OrdersDto;
import com.krishna9787.order_service.entity.Orders;
import com.krishna9787.order_service.exception.OrderNotFoundException;
import com.krishna9787.order_service.mapper.OrdersMapper;
import com.krishna9787.order_service.repository.OrderRepository;
import com.krishna9787.order_service.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    public static final String ORDER_CREATED_TOPIC = "order-created";
    private OrderRepository orderRepository;
    private final KafkaTemplate<String, OrdersDto> kafkaTemplate;

    public OrderServiceImpl(OrderRepository orderRepository, KafkaTemplate<String, OrdersDto> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void addOrder(OrdersDto orderDto) {
        Orders order = OrdersMapper.mapToOrders(orderDto, new Orders());
        order = orderRepository.save(order);
        publish(orderDto);
    }

    @Override
    public OrdersDto findOrderById(String value) {
        Orders order = orderRepository.findByOrderId(value)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        return OrdersMapper.mapToOrdersDto(order, new OrdersDto());
    }

    public void publish(OrdersDto ordersDto) {
        kafkaTemplate.send(
                ORDER_CREATED_TOPIC,
                ordersDto.getOrderId(),
                ordersDto)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        System.err.println(exception.getMessage());
                        return;
                    }
                    System.out.println("Message sent successfully");
                    System.out.println("Partition: " + result.getRecordMetadata().partition());
                    System.out.println("Offset: " + result.getRecordMetadata().offset());
                });
    }
}
