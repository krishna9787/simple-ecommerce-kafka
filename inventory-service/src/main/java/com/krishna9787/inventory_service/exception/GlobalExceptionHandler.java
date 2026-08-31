package com.krishna9787.inventory_service.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.krishna9787.inventory_service.dto.ErrorResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception ex, WebRequest request) {
                ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                                ex.getMessage(), request.getDescription(false), LocalDateTime.now());

                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(errorResponseDto);
        }

        @ExceptionHandler(InventoryNotFoundException.class)
        public ResponseEntity<ErrorResponseDto> handleInventoryNotFoundException(InventoryNotFoundException ex,
                        WebRequest request) {
                ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                                ex.getMessage(), request.getDescription(false), LocalDateTime.now());

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(errorResponseDto);
        }

        @ExceptionHandler(KafkaEventFailedException.class)
        public ResponseEntity<ErrorResponseDto> handleKafkaEventFailedException(KafkaEventFailedException ex,
                        WebRequest request) {
                ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                                ex.getMessage(), request.getDescription(false), LocalDateTime.now());

                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(errorResponseDto);
        }

        @ExceptionHandler(NotEnoughQuantityException.class)
        public ResponseEntity<ErrorResponseDto> handleNotEnoughQuantityException(NotEnoughQuantityException ex,
                        WebRequest request) {
                ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                                ex.getMessage(), request.getDescription(false), LocalDateTime.now());

                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(errorResponseDto);
        }
}
