package com.krishna9787.inventory_service.service;

import com.krishna9787.inventory_service.dto.InventoryDto;
import com.krishna9787.inventory_service.dto.OrderCreatedEventDto;

public interface InventoryService {

    void reserveInventory(OrderCreatedEventDto event);

    void addInventory(InventoryDto inventoryDto);
}
