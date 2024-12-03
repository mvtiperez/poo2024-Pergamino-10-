package com.example.demo.services;

import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AuthorizationServiceImp implements AuthorizationService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Override
    // implementar el método authorize
    public User authorize(String token) throws Exception {
        if (!jwtTokenUtil.validateToken(token)) {
            throw new Exception("Invalid token");
        }
        String username = jwtTokenUtil.getSubject(token);
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new Exception("User not found");
        }
        return user;
    }

    @Override
    // implementa el método verify
    public void verify(String token) {
        if (!jwtTokenUtil.validateToken(token)) {
            throw new RuntimeException("Invalid token");
        }
    }

}
