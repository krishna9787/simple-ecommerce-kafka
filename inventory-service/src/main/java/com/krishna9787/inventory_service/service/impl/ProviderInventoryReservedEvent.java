package com.krishna9787.inventory_service.service.impl;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.krishna9787.inventory_service.dto.InventoryStatusEventDto;
import com.krishna9787.inventory_service.exception.KafkaEventFailedException;

@Component
public class ProviderInventoryReservedEvent {

    private static final String topicName = "inventory-reserved";

    private final KafkaTemplate<String, InventoryStatusEventDto> kafkaTemplate;

    public ProviderInventoryReservedEvent(KafkaTemplate<String, InventoryStatusEventDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(InventoryStatusEventDto event) {
        kafkaTemplate.send(
                topicName,
                event.getOrderId(),
                event)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        throw new KafkaEventFailedException("Kafka Event Inventory Failed has issues");
                    }
                    System.out.println("Inventory Reserved. Event sent");
                });
    }
}
