package com.krishna9787.order_service.service;

import com.krishna9787.order_service.dto.InventoryReservedDto;
import com.krishna9787.order_service.dto.OrdersDto;

public interface OrderService {

    void addOrder(OrdersDto orderDto);

    OrdersDto findOrderById(String value);

    void handleReservedINventory(InventoryReservedDto inventoryReservedDto);
}
