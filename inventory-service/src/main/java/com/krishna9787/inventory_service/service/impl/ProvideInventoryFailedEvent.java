package com.krishna9787.inventory_service.service.impl;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.krishna9787.inventory_service.dto.InventoryStatusEventDto;
import com.krishna9787.inventory_service.exception.KafkaEventFailedException;

@Component
public class ProvideInventoryFailedEvent {
    public static final String topic = "inventory-failed";

    private final KafkaTemplate<String, InventoryStatusEventDto> kafkaTemplate;

    public ProvideInventoryFailedEvent(KafkaTemplate<String, InventoryStatusEventDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(InventoryStatusEventDto eventDto) {
        kafkaTemplate.send(
                topic,
                eventDto.getOrderId(),
                eventDto)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        throw new KafkaEventFailedException("Kafka Event Inventory Failed has issues");
                    }
                });
    }
}
