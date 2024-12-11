package com.example.demo.services;
import com.example.demo.Entitie.User;
public interface AuthorizationService {
    User authorize(String token) throws Exception;
    void verify(String token);
}
