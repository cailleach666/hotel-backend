package org.example.backend.exception.exceptions;

public class NoSuchPaymentException extends RuntimeException {
    public NoSuchPaymentException(String message) {
        super(message);
    }
}
