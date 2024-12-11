package com.example.demo.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.CreateUserRequestDTO;
import com.example.demo.Entitie.MusicArtistUser;
import com.example.demo.Entitie.MusicEnthusiastUser;
import com.example.demo.Entitie.User;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createArtist(CreateUserRequestDTO createUserRequestDTO) throws Exception {
        MusicArtistUser artistUser = modelMapper.map(createUserRequestDTO, MusicArtistUser.class);
        artistUser.setPassword(passwordEncoder.encode(artistUser.getPassword()));
        return userRepository.save(artistUser);
    }

    @Override
    public User createEnthusiast(CreateUserRequestDTO createUserRequestDTO) throws Exception {
        MusicEnthusiastUser enthusiastUser = modelMapper.map(createUserRequestDTO, MusicEnthusiastUser.class);
        enthusiastUser.setPassword(passwordEncoder.encode(enthusiastUser.getPassword()));
        return userRepository.save(enthusiastUser);
    }


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

}
