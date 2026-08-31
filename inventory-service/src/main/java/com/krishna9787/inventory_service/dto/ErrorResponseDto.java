package com.krishna9787.inventory_service.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDto {

    private String status;
    private String errorMessage;
    private String apiPath;
    private LocalDateTime timestamp;
}
