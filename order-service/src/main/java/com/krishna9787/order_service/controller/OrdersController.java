package com.krishna9787.order_service.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.krishna9787.order_service.dto.OrdersDto;
import com.krishna9787.order_service.dto.SuccessResponseDto;
import com.krishna9787.order_service.service.OrderService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@Validated
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class OrdersController {

    private OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity<SuccessResponseDto> postMethodName(@Valid @RequestBody OrdersDto ordersDto) {
        orderService.addOrder(ordersDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponseDto(HttpStatus.OK.toString(), "Order Saved successfully",
                        LocalDateTime.now()));
    }

}
