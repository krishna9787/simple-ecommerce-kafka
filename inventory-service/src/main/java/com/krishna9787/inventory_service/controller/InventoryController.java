package com.krishna9787.inventory_service.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krishna9787.inventory_service.dto.InventoryDto;
import com.krishna9787.inventory_service.dto.SuccessResponseDto;
import com.krishna9787.inventory_service.service.InventoryService;

@RestController
@Validated
@RequestMapping(path = "/api/inventory", produces = { MediaType.APPLICATION_JSON_VALUE })
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/add")
    public ResponseEntity<SuccessResponseDto> addInventory(@RequestBody InventoryDto inventoryDto) {
        System.out.println(inventoryDto);
        inventoryService.addInventory(inventoryDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SuccessResponseDto(HttpStatus.CREATED.toString(), "Inventory successfully added",
                        LocalDateTime.now()));
    }
}
