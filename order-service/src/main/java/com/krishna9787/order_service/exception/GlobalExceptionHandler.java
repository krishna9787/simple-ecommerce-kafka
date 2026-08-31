package com.krishna9787.order_service.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.krishna9787.order_service.dto.ErrorResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception ex,
                        WebRequest request) {
                ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                                HttpStatus.BAD_REQUEST.toString(),
                                ex.getMessage(),
                                LocalDateTime.now(),
                                request.getDescription(false));
                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(errorResponseDto);
        }

        @ExceptionHandler(OrderNotFoundException.class)
        public ResponseEntity<ErrorResponseDto> handleOrderNotFoundException(OrderNotFoundException ex,
                        WebRequest request) {
                ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                                HttpStatus.BAD_REQUEST.toString(),
                                ex.getMessage(),
                                LocalDateTime.now(),
                                request.getDescription(false));
                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(errorResponseDto);
        }
}
