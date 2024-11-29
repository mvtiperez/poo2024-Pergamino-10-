package com.example.demo.services;

import com.example.demo.DTO.CreateUserRequestDTO;
import com.example.demo.entities.User;

public interface UserService {
    User createArtist(CreateUserRequestDTO createUserRequestDTO) throws Exception;

    User createEnthusiast(CreateUserRequestDTO createUserRequestDTO) throws Exception;

    User findByUsername(String username);

    User findById(Long id);

    boolean deleteByUsername(String username);

}
