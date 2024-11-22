package ar.edu.unnoba.poo2024.allmusic.resource;

import java.net.Authenticator;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ar.edu.unnoba.poo2024.allmusic.services.AuthenticathionService;
import ar.edu.unnoba.poo2024.allmusic.dto.AuthenticationRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.dto.CreateUserRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.entities.MusicArtiesUser;
import ar.edu.unnoba.poo2024.allmusic.exceptions.AuthenticationException;
import ar.edu.unnoba.poo2024.allmusic.services.UserService;

@RestController
@RequestMapping("/artist")
public class MusicArtistUserResource {

    @Autowired

    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticathionService authenticationService;

    @PostMapping

    public ResponseEntity<?> createUser(@RequestBody CreateUserRequestDTO userRequestDto) {

        try {

            MusicArtiesUser musicArtistUser = modelMapper.map(userRequestDto, MusicArtiesUser.class);

            userService.create(musicArtistUser);

            return ResponseEntity.ok(musicArtistUser);

        } catch (Exception e) {

            return new ResponseEntity<>("Error al crear el usuario", HttpStatus.CONFLICT);
        }
    }

    @PostMapping(value = "/auth", produces = "application/json")
    public ResponseEntity<?> authentication(@RequestBody AuthenticationRequestDTO authenticationRequestDTO)
            throws Exception {
        try {

            MusicArtiesUser musicArtistUser = modelMapper.map(authenticationRequestDTO, MusicArtiesUser.class);

            String token = authenticationService.authenticate(musicArtistUser);

            // Retorna el token  formato JSON 
            return ResponseEntity.ok().body(Map.of("token", token));

        } catch (Exception e) {
            throw new AuthenticationException("Error al autenticar el usuario");
        }
    }
}
