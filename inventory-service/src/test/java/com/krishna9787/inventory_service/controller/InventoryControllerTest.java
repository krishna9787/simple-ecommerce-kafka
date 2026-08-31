package com.krishna9787.inventory_service.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.krishna9787.inventory_service.dto.InventoryDto;
import com.krishna9787.inventory_service.dto.SuccessResponseDto;
import com.krishna9787.inventory_service.service.InventoryService;

@ExtendWith(MockitoExtension.class)
class InventoryControllerTest {

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private InventoryController inventoryController;

    @Test
    void addInventory_shouldReturnCreatedResponseAndInvokeService() {
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setProductId("product-1");
        inventoryDto.setAvailableQuantity(10);
        inventoryDto.setReservedQuantity(0);

        ResponseEntity<SuccessResponseDto> response = inventoryController.addInventory(inventoryDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Inventory successfully added", response.getBody().getMessage());
        assertEquals("201 CREATED", response.getBody().getStatus());
        verify(inventoryService).addInventory(inventoryDto);
    }
}
