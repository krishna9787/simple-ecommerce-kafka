package com.krishna9787.order_service.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrdersDto {

    private String eventId;

    private String eventType;

    @NotNull(message = " Customer ID cannot be null")
    private String customerId;

    private BigDecimal amount;

    private String orderId;

    private String productId;

    private int quantity;
}
