package com.krishna9787.order_service.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessResponseDto {

    private String status;
    private String message;
    private LocalDateTime timeStamp;
}
