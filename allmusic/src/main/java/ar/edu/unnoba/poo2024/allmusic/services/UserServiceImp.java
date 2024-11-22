package ar.edu.unnoba.poo2024.allmusic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.poo2024.allmusic.entities.User;
import ar.edu.unnoba.poo2024.allmusic.repositories.UserRepository;
import ar.edu.unnoba.poo2024.allmusic.util.PasswordEncoder;

@Service
// En la clase UserServiceImp implementar el método public User
// findByUsername(String username).

public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void create(User user) throws Exception {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new Exception("ERROR");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User getReferenceId(Long id) {
        return userRepository.getReferenceById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
