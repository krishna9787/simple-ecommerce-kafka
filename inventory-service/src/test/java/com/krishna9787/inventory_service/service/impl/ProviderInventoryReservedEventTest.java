package com.krishna9787.inventory_service.service.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import com.krishna9787.inventory_service.dto.InventoryReservedEventDto;

@ExtendWith(MockitoExtension.class)
class ProviderInventoryReservedEventTest {

    @Mock
    private KafkaTemplate<String, InventoryReservedEventDto> kafkaTemplate;

    @InjectMocks
    private ProviderInventoryReservedEvent providerInventoryReservedEvent;

    @Test
    void publish_shouldSendEventToInventoryReservedTopic() {
        InventoryReservedEventDto event = new InventoryReservedEventDto("order-202", "Inventory Reserved");
        CompletableFuture<SendResult<String, InventoryReservedEventDto>> future = CompletableFuture
                .completedFuture(null);

        when(kafkaTemplate.send("inventory-reserved", event.getOrderId(), event)).thenReturn(future);

        providerInventoryReservedEvent.publish(event);

        verify(kafkaTemplate).send("inventory-reserved", event.getOrderId(), event);
    }
}
