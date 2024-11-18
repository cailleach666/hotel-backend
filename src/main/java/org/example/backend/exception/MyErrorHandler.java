package org.example.backend.exception;

import org.example.backend.exception.exceptions.ClientEmailAlreadyExistsException;
import org.example.backend.exception.exceptions.NoSuchClientException;
import org.example.backend.exception.exceptions.NoSuchReservationException;
import org.example.backend.exception.exceptions.NoSuchRoomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyErrorHandler {
    @ExceptionHandler({NoSuchClientException.class, NoSuchRoomException.class, NoSuchReservationException.class})
    public ResponseEntity<Object> handleException(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClientEmailAlreadyExistsException.class)
    public ResponseEntity<Object> handleClientEmailAlreadyExists(ClientEmailAlreadyExistsException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
