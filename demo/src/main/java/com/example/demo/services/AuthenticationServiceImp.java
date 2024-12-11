package com.example.demo.services;

import com.example.demo.DTO.AuthenticationRequestDTO;
import com.example.demo.Entitie.User;
import com.example.demo.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServiceImp implements AuthenticationService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Override
    public Map<String, String> authenticate(AuthenticationRequestDTO request) {
        // Verificar si el usuario existe
        User user = userService.findByUsername(request.getUsername());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Validar la contraseña
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Generar el token
        String userType = user.getUserType();
        String token = jwtTokenUtil.generateToken(user.getId().toString(), userType);
        String username=user.getUsername();
        // Retornar token y tipo de usueario
        Map<String, String> authResult = new HashMap<>();
        authResult.put("token", token);
        authResult.put("userType", userType);
        authResult.put("Username", username);
        return authResult;
    }
}
