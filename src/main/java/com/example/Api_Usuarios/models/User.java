package com.example.Api_Usuarios.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    // Quitamos @NotBlank aquí porque el ID es el identificador de la DB,
    // pero si lo vas a enviar manualmente, asegúrate de validarlo en el DTO.
    private String id;

    @NotBlank(message = "El nombre es obligatorio")
    private String firstName;

    private String secondName;

    @NotBlank(message = "El apellido es obligatorio")
    private String firstLastName;

    private String secondLastName;

    @Past(message = "Fecha de nacimiento incorrecta")
    private LocalDate birthDate;

    @NotBlank(message = "el departamento es obligatorio")
    private String state;

    @NotBlank(message = "la ciudad es obligatoria")
    private String city;

    @Column(unique = true, nullable = false)
    @Email(message = "El formato del email no es valido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    // 🔗 Relación corregida para ser dinámica
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Builder.Default // Importante para que Lombok no ignore la inicialización al usar Builder
    private Set<Role> roles = new HashSet<>();



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
