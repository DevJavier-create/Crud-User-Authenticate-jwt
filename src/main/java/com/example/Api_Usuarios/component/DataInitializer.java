package com.example.Api_Usuarios.component;

import com.example.Api_Usuarios.models.Role;
import com.example.Api_Usuarios.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    public  DataInitializer(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        // 🛡️ Verificamos si el rol de usuario ya existe
        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            roleRepository.save(Role.builder().name("ROLE_USER").build());
            System.out.println("✅ Rol ROLE_USER creado.");
        }

        // 👑 Verificamos si el rol de admin ya existe
        if (roleRepository.findByName("ROLE_SUPER_ADMIN").isEmpty()) {
            roleRepository.save(Role.builder().name("ROLE_SUPER_ADMIN").build());
            System.out.println("✅ Rol ROLE_SUPER_ADMIN creado.");
        }
    }
}
