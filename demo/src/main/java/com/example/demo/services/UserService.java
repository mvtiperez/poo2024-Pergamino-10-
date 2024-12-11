package com.example.demo.services;

import com.example.demo.DTO.CreateUserRequestDTO;
import com.example.demo.Entitie.User;

public interface UserService {
    User createArtist(CreateUserRequestDTO createUserRequestDTO) throws Exception;
    User createEnthusiast(CreateUserRequestDTO createUserRequestDTO) throws Exception;
    User findByUsername(String username);
    User findById(Long id);
}
