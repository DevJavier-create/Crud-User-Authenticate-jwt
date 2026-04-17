package com.example.Api_Usuarios.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String timestamp;
    private String code;
    private String message;
    private int status;
}
