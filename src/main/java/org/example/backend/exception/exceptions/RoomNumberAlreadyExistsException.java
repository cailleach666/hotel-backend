package org.example.backend.exception.exceptions;

public class RoomNumberAlreadyExistsException extends RuntimeException {
    public RoomNumberAlreadyExistsException(String message) {
        super(message);
    }
}
