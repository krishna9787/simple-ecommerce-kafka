package com.krishna9787.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryStatusDto {
    private String orderId;
    private String status;
    private String reason;
}
