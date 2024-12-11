package com.example.demo.services;

import java.util.Map;

import com.example.demo.DTO.AuthenticationRequestDTO;

public interface AuthenticationService {
    Map authenticate(AuthenticationRequestDTO requestDTO) throws Exception;
}
