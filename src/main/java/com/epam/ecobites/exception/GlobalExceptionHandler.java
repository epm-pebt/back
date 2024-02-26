package com.epam.ecobites.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    //Just an example of how to handle exceptions
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleException(RuntimeException exception) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
