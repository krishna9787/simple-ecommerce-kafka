package com.krishna9787.order_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.krishna9787.order_service.dto.InventoryReservedDto;
import com.krishna9787.order_service.service.OrderService;

@Component
public class ProcessInventoryReserved {

    @Autowired
    private OrderService orderservice;

    @KafkaListener(topics = "inventory-reserved", groupId = "inventory-reserved-group")
    public void processInventoryReserved(InventoryReservedDto inventoryReservedDto) {
        System.out.println("Inventory reserved: " + inventoryReservedDto.getStatus());
        orderservice.handleReservedINventory(inventoryReservedDto);
    }
}
