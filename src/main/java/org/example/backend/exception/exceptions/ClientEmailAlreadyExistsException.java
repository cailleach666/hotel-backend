package org.example.backend.exception.exceptions;

public class ClientEmailAlreadyExistsException extends RuntimeException {
    public ClientEmailAlreadyExistsException(String message) {
        super(message);
    }
}
