package com.krishna9787.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryStatusEventDto {

    private String orderId;
    private String status;
    private String reason;
}
