package com.krishna9787.order_service.service;

import com.krishna9787.order_service.dto.InventoryStatusDto;
import com.krishna9787.order_service.dto.OrdersDto;

public interface OrderService {

    void addOrder(OrdersDto orderDto);

    OrdersDto findOrderById(String value);

    void handleInventoryStatus(InventoryStatusDto inventoryReservedDto);
}
