package ar.edu.unnoba.poo2024.allmusic.services;


import ar.edu.unnoba.poo2024.allmusic.entities.User;

public interface AuthorizationService {
    public User authorize(String token) throws Exception;
}
