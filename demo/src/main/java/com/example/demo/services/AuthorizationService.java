package com.example.demo.services;
import com.example.demo.entities.User;
public interface AuthorizationService {
    User authorize(String token) throws Exception;
    void verify(String token);
}
