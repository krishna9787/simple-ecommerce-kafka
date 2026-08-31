package com.krishna9787.order_service.mapper;

import com.krishna9787.order_service.dto.OrdersDto;
import com.krishna9787.order_service.entity.Orders;

public class OrdersMapper {

    public static OrdersDto mapToOrdersDto(Orders orders, OrdersDto ordersDto) {

        ordersDto.setCustomerId(orders.getCustomerId());
        ordersDto.setEventId(orders.getEventId());
        ordersDto.setEventType(orders.getEventType());
        ordersDto.setAmount(orders.getAmount());
        ordersDto.setOrderId(orders.getOrderId());
        ordersDto.setProductId(orders.getProductId());
        ordersDto.setQuantity(orders.getQuantity());

        return ordersDto;
    }

    public static Orders mapToOrders(OrdersDto ordersDto, Orders orders) {

        orders.setCustomerId(ordersDto.getCustomerId());
        orders.setEventId(ordersDto.getEventId());
        orders.setEventType(ordersDto.getEventType());
        orders.setAmount(ordersDto.getAmount());
        orders.setOrderId(ordersDto.getOrderId());
        orders.setProductId(ordersDto.getProductId());
        orders.setQuantity(ordersDto.getQuantity());

        return orders;
    }
}
