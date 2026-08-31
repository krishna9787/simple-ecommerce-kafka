package com.krishna9787.inventory_service.mapper;

import com.krishna9787.inventory_service.dto.InventoryDto;
import com.krishna9787.inventory_service.entity.Inventory;

public class InventoryMapper {

    public static Inventory mapToInventory(Inventory inventory, InventoryDto inventoryDto) {
        inventory.setProductId(inventoryDto.getProductId());
        inventory.setAvailableQuantity(inventoryDto.getAvailableQuantity());
        inventory.setReservedQuantity(inventoryDto.getReservedQuantity());

        return inventory;
    }

    public static InventoryDto mapToInventoryDto(InventoryDto inventoryDto, Inventory inventory) {

        inventoryDto.setProductId(inventory.getProductId());
        inventoryDto.setAvailableQuantity(inventory.getAvailableQuantity());
        inventoryDto.setReservedQuantity(inventory.getReservedQuantity());

        return inventoryDto;
    }
}
