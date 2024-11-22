package ar.edu.unnoba.poo2024.allmusic.services;

import org.springframework.stereotype.Service;
import ar.edu.unnoba.poo2024.allmusic.entities.User;

@Service
public interface AuthenticathionService {

    public String authenticate(User user) throws Exception;
}
