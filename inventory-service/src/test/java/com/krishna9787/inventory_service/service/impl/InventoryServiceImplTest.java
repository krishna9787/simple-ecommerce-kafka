package com.krishna9787.inventory_service.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.krishna9787.inventory_service.dto.InventoryDto;
import com.krishna9787.inventory_service.dto.InventoryFailedEventDto;
import com.krishna9787.inventory_service.dto.InventoryReservedEventDto;
import com.krishna9787.inventory_service.dto.OrderCreatedEventDto;
import com.krishna9787.inventory_service.entity.Inventory;
import com.krishna9787.inventory_service.exception.InventoryNotFoundException;
import com.krishna9787.inventory_service.exception.NotEnoughQuantityException;
import com.krishna9787.inventory_service.repository.InventoryRepository;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private ProvideInventoryFailedEvent provideInventoryFailedEvent;

    @Mock
    private ProviderInventoryReservedEvent providerInventoryReservedEvent;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private OrderCreatedEventDto orderCreatedEvent;

    @BeforeEach
    void setUp() {
        orderCreatedEvent = new OrderCreatedEventDto(
                "event-1",
                "ORDER_CREATED",
                "customer-1",
                new BigDecimal("150.00"),
                "order-10",
                "product-1",
                3);
    }

    @Test
    void reserveInventory_shouldUpdateInventoryAndPublishReservedEvent_whenStockIsAvailable() {
        Inventory inventory = new Inventory();
        inventory.setProductId("product-1");
        inventory.setAvailableQuantity(10);
        inventory.setReservedQuantity(2);

        when(inventoryRepository.findByProductId("product-1")).thenReturn(Optional.of(inventory));

        inventoryService.reserveInventory(orderCreatedEvent);

        assertEquals(7, inventory.getAvailableQuantity());
        assertEquals(5, inventory.getReservedQuantity());
        verify(providerInventoryReservedEvent).publish(argThat(
                event -> "order-10".equals(event.getOrderId()) && "Inventory Reserved".equals(event.getStatus())));
    }

    @Test
    void reserveInventory_shouldPublishFailureAndThrow_whenProductDoesNotExist() {
        when(inventoryRepository.findByProductId("product-1")).thenReturn(Optional.empty());

        InventoryNotFoundException exception = assertThrows(
                InventoryNotFoundException.class,
                () -> inventoryService.reserveInventory(orderCreatedEvent));

        assertEquals("Inventory Not Found Exception", exception.getMessage());
        verify(provideInventoryFailedEvent).publish(argThat(event -> "order-10".equals(event.getOrderId())
                && "Failed".equals(event.getStatus())
                && "Product not Found".equals(event.getReason())));
        verify(providerInventoryReservedEvent, never()).publish(any());
    }

    @Test
    void reserveInventory_shouldPublishFailureAndThrow_whenQuantityIsInsufficient() {
        Inventory inventory = new Inventory();
        inventory.setProductId("product-1");
        inventory.setAvailableQuantity(2);
        inventory.setReservedQuantity(1);

        when(inventoryRepository.findByProductId("product-1")).thenReturn(Optional.of(inventory));

        NotEnoughQuantityException exception = assertThrows(
                NotEnoughQuantityException.class,
                () -> inventoryService.reserveInventory(orderCreatedEvent));

        assertEquals("Not Enough quantity to fulfill the order", exception.getMessage());
        verify(provideInventoryFailedEvent).publish(argThat(event -> "order-10".equals(event.getOrderId())
                && "Failed".equals(event.getStatus())
                && "Not Enough Quantity".equals(event.getReason())));
        verify(providerInventoryReservedEvent, never()).publish(any());
    }

    @Test
    void addInventory_shouldSaveMappedInventory() {
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setProductId("product-2");
        inventoryDto.setAvailableQuantity(15);
        inventoryDto.setReservedQuantity(0);

        inventoryService.addInventory(inventoryDto);

        ArgumentCaptor<Inventory> inventoryCaptor = ArgumentCaptor.forClass(Inventory.class);
        verify(inventoryRepository).save(inventoryCaptor.capture());

        Inventory savedInventory = inventoryCaptor.getValue();
        assertEquals("product-2", savedInventory.getProductId());
        assertEquals(15, savedInventory.getAvailableQuantity());
        assertEquals(0, savedInventory.getReservedQuantity());
    }
}
