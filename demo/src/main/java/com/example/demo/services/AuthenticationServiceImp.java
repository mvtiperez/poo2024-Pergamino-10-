package com.example.demo.services;

import com.example.demo.DTO.AuthenticationRequestDTO;
import com.example.demo.entities.User;
import com.example.demo.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImp implements AuthenticationService {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Override
    public String authenticate(AuthenticationRequestDTO request) {
        
        User user = userService.findByUsername(request.getUsername());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Validar la contraseña
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Generar y devolver el token
        String userType = user.getUserType();
        return jwtTokenUtil.generateToken(user.getId().toString(), userType);
    }
}
