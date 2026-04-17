package com.example.Api_Usuarios.services;

import com.example.Api_Usuarios.models.User;
import com.example.Api_Usuarios.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true) // Optimiza la consulta en MariaDB
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}