package com.example.Api_Usuarios.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String id;
    private String firstName;
    private String firstLastName; // 👈 Agrégalo
    private String email;
    private String password;
    private String state;        // 👈 Agrégalo
    private String city;         // 👈 Agrégalo
}