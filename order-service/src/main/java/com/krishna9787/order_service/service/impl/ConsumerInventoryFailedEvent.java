package com.krishna9787.order_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.krishna9787.order_service.dto.InventoryStatusDto;
import com.krishna9787.order_service.service.OrderService;

@Component
public class ConsumerInventoryFailedEvent {

    @Autowired
    private OrderService orderservice;

    @KafkaListener(topics = "${inventory-service.consumers.consumer2.topic}", groupId = "${inventory-service.consumers.consumer2.group-id}")
    public void processInventoryFailed(InventoryStatusDto inventoryReservedDto) {
        System.out.println("Inventory failed event received.");
        orderservice.handleInventoryStatus(inventoryReservedDto);
    }
}
