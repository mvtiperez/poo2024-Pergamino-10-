package com.example.demo.services;

import com.example.demo.DTO.CreateUserRequestDTO;
import com.example.demo.entities.User;

// esta interfaz define los métodos que se pueden usar para interactuar con los usuarios
public interface UserService {
    User createArtist(CreateUserRequestDTO createUserRequestDTO) throws Exception;

    User createEnthusiast(CreateUserRequestDTO createUserRequestDTO) throws Exception;

    User findByUsername(String username);

    User findById(Long id);

    boolean deleteByUsername(String username);

}
