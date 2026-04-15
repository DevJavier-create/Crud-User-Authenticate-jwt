package com.example.Api_Usuarios.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(BadCredentialsException e) {
        // Devolvemos un mensaje genérico por seguridad
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Error: Credenciales de inicio de sesión inválidas.");
    }
}
