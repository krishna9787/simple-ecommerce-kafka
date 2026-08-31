package com.krishna9787.inventory_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InventoryDto {

    private String productId;

    private long availableQuantity;

    private long reservedQuantity;
}
