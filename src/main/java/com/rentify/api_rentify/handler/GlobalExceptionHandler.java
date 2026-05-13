package com.rentify.api_rentify.handler;

import com.rentify.api_rentify.exception.UsuarioYaExisteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioYaExisteException.class)
    public ResponseEntity<String> handleUsuarioYaExisteException(UsuarioYaExisteException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT); // Devuelve 409
    }
}