package com.krishna9787.order_service.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {

    private String status;
    private String message;
    private LocalDateTime timestamp;
    private String apiPath;

}
