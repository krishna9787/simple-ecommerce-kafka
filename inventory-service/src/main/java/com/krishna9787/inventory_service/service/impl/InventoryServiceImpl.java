package com.krishna9787.inventory_service.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.krishna9787.inventory_service.dto.InventoryDto;
import com.krishna9787.inventory_service.dto.InventoryFailedEventDto;
import com.krishna9787.inventory_service.dto.InventoryReservedEventDto;
import com.krishna9787.inventory_service.dto.OrderCreatedEventDto;
import com.krishna9787.inventory_service.entity.Inventory;
import com.krishna9787.inventory_service.exception.InventoryNotFoundException;
import com.krishna9787.inventory_service.exception.NotEnoughQuantityException;
import com.krishna9787.inventory_service.mapper.InventoryMapper;
import com.krishna9787.inventory_service.repository.InventoryRepository;
import com.krishna9787.inventory_service.service.InventoryService;

import jakarta.transaction.Transactional;

@Service
public class InventoryServiceImpl implements InventoryService {

    private InventoryRepository inventoryRepository;
    private ProvideInventoryFailedEvent provideInventoryFailedEvent;
    private ProviderInventoryReservedEvent providerInventoryReservedEvent;

    public InventoryServiceImpl(InventoryRepository inventoryRepository,
            ProvideInventoryFailedEvent provideInventoryFailedEvent,
            ProviderInventoryReservedEvent providerInventoryReservedEvent) {
        this.inventoryRepository = inventoryRepository;
        this.provideInventoryFailedEvent = provideInventoryFailedEvent;
        this.providerInventoryReservedEvent = providerInventoryReservedEvent;
    }

    @Override
    @Transactional
    public void reserveInventory(OrderCreatedEventDto event) {
        Optional<Inventory> inventoryOptional = inventoryRepository.findByProductId(event.getProductId());

        if (inventoryOptional.isEmpty()) {
            handleProductNotFound(event);
        }
        Inventory inventory = inventoryOptional.get();

        if (inventory.getAvailableQuantity() < event.getQuantity()) {
            handleNotEnoughQuantity(event);
        }
        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() - event.getQuantity());

        inventory.setReservedQuantity(inventory.getReservedQuantity() + event.getQuantity());

        providerInventoryReservedEvent.publish(new InventoryReservedEventDto(event.getOrderId(), "Inventory Reserved"));
    }

    public void handleProductNotFound(OrderCreatedEventDto event) {
        provideInventoryFailedEvent
                .publish(new InventoryFailedEventDto(event.getOrderId(), "Failed", "Product not Found"));
        throw new InventoryNotFoundException("Inventory Not Found Exception");
    }

    public void handleNotEnoughQuantity(OrderCreatedEventDto event) {
        provideInventoryFailedEvent
                .publish(new InventoryFailedEventDto(event.getOrderId(), "Failed", "Not Enough Quantity"));
        throw new NotEnoughQuantityException("Not Enough quantity to fulfill the order");
    }

    @Override
    public void addInventory(InventoryDto inventoryDto) {
        Inventory inventory = InventoryMapper.mapToInventory(new Inventory(), inventoryDto);

        inventoryRepository.save(inventory);

    }

}
