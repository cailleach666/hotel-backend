package org.example.backend.exception.exceptions;

public class NoSuchRoomException extends RuntimeException {
    public NoSuchRoomException(String message) {
        super(message);
    }
}
