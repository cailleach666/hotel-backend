package org.example.backend.exception.exceptions;

public class AmenityNameAlreadyExistsException extends RuntimeException {
    public AmenityNameAlreadyExistsException(String message) {
        super(message);
    }
}
