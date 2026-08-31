package com.krishna9787.inventory_service.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SuccessResponseDto {

    private String status;
    private String message;
    private LocalDateTime timestamp;
}
