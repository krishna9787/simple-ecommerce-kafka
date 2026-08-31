package com.krishna9787.inventory_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class NotEnoughQuantityException extends RuntimeException {
    public NotEnoughQuantityException(String message) {
        super(message);
    }
}
