package com.krishna9787.inventory_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class KafkaEventFailedException extends RuntimeException {
    public KafkaEventFailedException(String message) {
        super(message);
    }
}
