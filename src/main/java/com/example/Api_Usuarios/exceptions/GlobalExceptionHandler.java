package com.example.Api_Usuarios.exceptions;

import com.example.Api_Usuarios.DTO.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Errores de lógica (como el email duplicado que lanzas tú)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex) {
        return buildResponse("BUSINESS_ERROR", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 2. Errores de validación (cuando falta un campo en el JSON)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult().getFieldError().getDefaultMessage();
        return buildResponse("VALIDATION_ERROR", mensaje, HttpStatus.BAD_REQUEST);
    }

    // 3. EL COMODÍN: Cualquier otro error inesperado (Error 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex) {
        return buildResponse("INTERNAL_SERVER_ERROR", "Ocurrió un error inesperado en el servidor", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Método auxiliar para no repetir código
    private ResponseEntity<ErrorResponse> buildResponse(String code, String msg, HttpStatus status) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .code(code)
                .message(msg)
                .status(status.value())
                .build();
        return new ResponseEntity<>(error, status);
    }
}
