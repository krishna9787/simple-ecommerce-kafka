package com.krishna9787.inventory_service.service.impl;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.krishna9787.inventory_service.dto.OrderCreatedEventDto;
import com.krishna9787.inventory_service.service.InventoryService;

@Component
public class ConsumeOrderEvent {

    private InventoryService inventoryService;

    public ConsumeOrderEvent(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = "order-created", groupId = "inventory-service")
    public void consumeOrderCreated(OrderCreatedEventDto event) {
        System.out.println("Received Order Created Event: " + event.getOrderId());
        inventoryService.reserveInventory(event);
    }

}
