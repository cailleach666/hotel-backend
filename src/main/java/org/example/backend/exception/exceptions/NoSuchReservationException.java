package org.example.backend.exception.exceptions;

public class NoSuchReservationException extends RuntimeException {
    public NoSuchReservationException(String message) {
        super(message);
    }
}
