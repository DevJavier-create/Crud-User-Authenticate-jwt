package com.example.Api_Usuarios.config;

import com.example.Api_Usuarios.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor // Genera el constructor para inyectar los componentes final
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactivamos CSRF (innecesario con JWT)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Rutas públicas (login/registro)
                        .requestMatchers("/api/admin/**").hasAuthority("ROLE_SUPER_ADMIN")
                        .anyRequest().authenticated() // Todo lo demás requiere autenticación
                )
                // 1. Configuramos la sesión como Stateless
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 2. Usamos el proveedor de autenticación que busca en MariaDB
                .authenticationProvider(authenticationProvider)
                // 3. Añadimos nuestro filtro JWT antes del filtro de usuario/password estándar
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}