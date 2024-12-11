package com.example.demo.controlador;

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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO request) {
        try {
            // El servicio devuelve un Map con el token y el tipo de usuario
            Map<String, String> authResult = authenticationService.authenticate(request);
            String token = authResult.get("token");
            String userType = authResult.get("userType");
            String username= authResult.get("Username");

            return ResponseEntity.ok()
                .header("Authorization", token)
                .body(Map.of(
                    "message", "Login successful",
                    "token", token,
                    "userType", userType,
                    "Username",username
                ));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
    }
}

