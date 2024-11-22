package ar.edu.unnoba.poo2024.allmusic.services;

import ar.edu.unnoba.poo2024.allmusic.entities.User;

public interface UserService {
    public void create(User user) throws Exception;

    // En la interface UserService, declarar el método public User
    // findByUsername(String username)
    public User findByUsername(String username);
}
