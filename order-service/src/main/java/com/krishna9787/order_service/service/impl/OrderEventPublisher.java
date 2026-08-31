package com.krishna9787.order_service.service.impl;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.krishna9787.order_service.dto.OrdersDto;

@Service
public class OrderEventPublisher {

    public static final String topic = "order-created";

    private final KafkaTemplate<String, OrdersDto> kafkaTemplate;

    public OrderEventPublisher(KafkaTemplate<String, OrdersDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(OrdersDto ordersDto) {
        kafkaTemplate.send(
                topic,
                ordersDto.getOrderId(),
                ordersDto)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        System.err.println(
                                "Failed to send Kafka message: " + exception.getMessage());
                        return;
                    }
                    System.out.println("Kafka message sent successfully");
                    System.out.println("Partition: " + result.getRecordMetadata().partition());
                    System.out.println("Offset: " + result.getRecordMetadata().offset());
                });
    }
}
