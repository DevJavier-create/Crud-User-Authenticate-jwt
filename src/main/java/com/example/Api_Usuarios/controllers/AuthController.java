package com.example.Api_Usuarios.controllers;

import com.example.Api_Usuarios.DTO.AuthResponse;
import com.example.Api_Usuarios.auth.AuthenticationRequest;
import com.example.Api_Usuarios.auth.AuthenticationResponse;
import com.example.Api_Usuarios.auth.RegisterRequest;
import com.example.Api_Usuarios.models.User;
import com.example.Api_Usuarios.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usuario registrado con exito");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate (@RequestBody AuthenticationRequest request){
        return  ResponseEntity.ok(authService.authenticate(request));
    }
}
