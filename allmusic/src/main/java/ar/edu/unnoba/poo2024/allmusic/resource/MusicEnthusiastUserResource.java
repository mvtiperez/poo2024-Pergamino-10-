package ar.edu.unnoba.poo2024.allmusic.resource;

import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unnoba.poo2024.allmusic.dto.AuthenticationRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.dto.CreateUserRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.entities.MusicArtiesUser;
import ar.edu.unnoba.poo2024.allmusic.entities.MusicEnthusiastUser;
import ar.edu.unnoba.poo2024.allmusic.exceptions.AuthenticationException;
import ar.edu.unnoba.poo2024.allmusic.services.AuthenticathionService;
import ar.edu.unnoba.poo2024.allmusic.services.UserService;

@RestController
@RequestMapping("/enthusiat")
public class MusicEnthusiastUserResource {

    @Autowired

    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticathionService authenticationService;

    @PostMapping

    public ResponseEntity<String> createUser(@RequestBody CreateUserRequestDTO userRequestDto) {

        try {
            MusicEnthusiastUser musicEnthusiastUser = modelMapper.map(userRequestDto, MusicEnthusiastUser.class);
            userService.create(musicEnthusiastUser);
            return new ResponseEntity<>("El usuario " + musicEnthusiastUser.getUsername() + " se creo correctamente",
                    HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear el usuario", HttpStatus.CONFLICT);
        }
    }

    @PostMapping(value = "/auth", produces = "application/json")
    public ResponseEntity<?> authentication(@RequestBody AuthenticationRequestDTO authenticationRequestDTO)
            throws Exception {
        try {

            MusicEnthusiastUser musicEnthusiastUser = modelMapper.map(authenticationRequestDTO,
                    MusicEnthusiastUser.class);
            String token = authenticationService.authenticate(musicEnthusiastUser);
            return ResponseEntity.ok().body(Map.of("token", token));

        } catch (AuthenticationException auth) {
            return new ResponseEntity<>("Error al autenticar usuario", HttpStatus.UNAUTHORIZED);
        }
    }
}
