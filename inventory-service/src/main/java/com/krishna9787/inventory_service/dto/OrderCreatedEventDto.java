package com.krishna9787.inventory_service.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEventDto {
    private String eventId;

    private String eventType;

    private String customerId;

    private BigDecimal amount;

    private String orderId;

    private String productId;

    private int quantity;
}
