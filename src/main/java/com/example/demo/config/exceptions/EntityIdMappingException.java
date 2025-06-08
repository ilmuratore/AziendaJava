package com.example.demo.config.exceptions;

public class EntityIdMappingException extends RuntimeException {
    public EntityIdMappingException(String message) {
        super(message);
    }
    public EntityIdMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}