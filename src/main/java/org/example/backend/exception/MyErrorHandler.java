package org.example.backend.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.exception.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class MyErrorHandler {
    @ExceptionHandler({NoSuchClientException.class,
            NoSuchRoomException.class,
            NoSuchReservationException.class
            })

    private void logException(Exception ex) {
        log.error("Exception occurred: {}", ex.getMessage(), ex);
    }

    public ResponseEntity<Object> handleException(Exception ex) {
        ex.printStackTrace();
        logException(ex);
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClientEmailAlreadyExistsException.class)
    public ResponseEntity<Object> handleClientEmailAlreadyExists(ClientEmailAlreadyExistsException ex) {
        ex.printStackTrace();
        logException(ex);
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoomNumberAlreadyExistsException.class)
    public ResponseEntity<Object> handleRoomNumberAlreadyExists(RoomNumberAlreadyExistsException ex) {
        ex.printStackTrace();
        logException(ex);
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidNumberOfGuestsException.class)
    public ResponseEntity<Object> handleLimitException(Exception ex) {
        ex.printStackTrace();
        logException(ex);
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
