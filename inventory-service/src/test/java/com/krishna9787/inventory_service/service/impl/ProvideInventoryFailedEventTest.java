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

import com.krishna9787.inventory_service.dto.InventoryStatusEventDto;

@ExtendWith(MockitoExtension.class)
class ProvideInventoryFailedEventTest {

    @Mock
    private KafkaTemplate<String, InventoryStatusEventDto> kafkaTemplate;

    @InjectMocks
    private ProvideInventoryFailedEvent provideInventoryFailedEvent;

    @Test
    void publish_shouldSendEventToInventoryFailedTopic() {
        InventoryStatusEventDto event = new InventoryStatusEventDto("order-101", "Failed", "Product not Found");
        CompletableFuture<SendResult<String, InventoryStatusEventDto>> future = CompletableFuture.completedFuture(null);

        when(kafkaTemplate.send(ProvideInventoryFailedEvent.topic, event.getOrderId(), event)).thenReturn(future);

        provideInventoryFailedEvent.publish(event);

        verify(kafkaTemplate).send(ProvideInventoryFailedEvent.topic, event.getOrderId(), event);
    }
}
