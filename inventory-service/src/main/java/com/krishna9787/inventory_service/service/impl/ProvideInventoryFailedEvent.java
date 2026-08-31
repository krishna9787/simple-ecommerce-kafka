package com.krishna9787.inventory_service.service.impl;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.krishna9787.inventory_service.dto.InventoryFailedEventDto;
import com.krishna9787.inventory_service.exception.KafkaEventFailedException;

@Component
public class ProvideInventoryFailedEvent {
    public static final String topic = "inventory-failed";

    private final KafkaTemplate<String, InventoryFailedEventDto> kafkaTemplate;

    public ProvideInventoryFailedEvent(KafkaTemplate<String, InventoryFailedEventDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(InventoryFailedEventDto eventDto) {
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
