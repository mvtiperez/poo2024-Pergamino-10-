package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import com.example.demo.DTO.AuthenticationRequestDTO;
import com.example.demo.services.AuthenticationService;

@RestController
@RequestMapping("/api/auth")

public class AuthenticationResource {

    @Autowired
    private AuthenticationService authenticationService;

    // implementación del método login
    // este método recibe un objeto AuthenticationRequestDTO y retorna un
    // ResponseEntity
    // con un mensaje y un token en caso de que la autenticación sea exitosa
    // en caso contrario, retorna un ResponseEntity con un mensaje de error
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO request) {
        try {
            String token = authenticationService.authenticate(request);
            return ResponseEntity.ok().header("Authorization", token)
                    .body(Map.of("message", "Login successful", "token", token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
