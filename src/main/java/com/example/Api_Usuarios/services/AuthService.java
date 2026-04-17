package com.example.Api_Usuarios.services;

import com.example.Api_Usuarios.DTO.AuthResponse;
import com.example.Api_Usuarios.models.Role;
import com.example.Api_Usuarios.models.User;
import com.example.Api_Usuarios.repositories.RoleRepository;
import com.example.Api_Usuarios.repositories.UserRepository;
import com.example.Api_Usuarios.security.JwtService;
// Importa tus DTOs (asegúrate de que existan en tu paquete)
import com.example.Api_Usuarios.auth.AuthenticationRequest;
import com.example.Api_Usuarios.auth.AuthenticationResponse;
import com.example.Api_Usuarios.auth.RegisterRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    // MÉTODO DE REGISTRO
    public void register(RegisterRequest request) {

        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Error: el correo electronico ya se encuentra registrado");
        }

        Role userRole = roleRepository.findByName("ROLE_USER") // Es estándar usar el prefijo ROLE_
                .orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_USER").build()));

        var user = User.builder()
                .id(request.getId())
                .firstName(request.getFirstName())
                .firstLastName(request.getFirstLastName()) // 👈 Mapea el campo
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .state(request.getState())                 // 👈 Mapea el campo
                .city(request.getCity())                   // 👈 Mapea el campo
                .roles(Set.of(userRole))
                .build();

        userRepository.save(user);
    }

    // MÉTODO DE LOGIN (AUTENTICACIÓN)
    public AuthResponse authenticate(AuthenticationRequest request) {
        // El Manager se encarga de validar email y password automáticamente
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Si el Manager no lanzó error, el usuario es válido
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .type("Bearer")
                .email(user.getEmail())
                .role(user.getAuthorities().toString()) // O el rol que manejes
                .expiresIn(3600000) // Ejemplo: 1 hora
                .build();
    }
}