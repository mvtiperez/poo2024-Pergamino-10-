package com.example.demo.services;

import com.example.demo.DTO.AuthenticationRequestDTO;

public interface AuthenticationService {
    String authenticate(AuthenticationRequestDTO requestDTO) throws Exception;
}
