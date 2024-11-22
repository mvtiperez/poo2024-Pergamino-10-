package ar.edu.unnoba.poo2024.allmusic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.password4j.Password;
import ar.edu.unnoba.poo2024.allmusic.entities.User;
import ar.edu.unnoba.poo2024.allmusic.exceptions.PasswordEncoderExcepcion;
import ar.edu.unnoba.poo2024.allmusic.exceptions.UserPrincipalException;
import ar.edu.unnoba.poo2024.allmusic.util.JwtTokenUtil;
import ar.edu.unnoba.poo2024.allmusic.util.PasswordEncoder;

@Service
public class AuthenticathionServiceImp implements AuthenticathionService {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    public String authenticate(User user) throws Exception {

        User storedUser = userService.findByUsername(user.getUsername());

        if (storedUser == null) {
            throw new UserPrincipalException("El usuario no fue encontrado.");
        }

        // Utilizar instancia de la clase PasswordEncoder para verificar el password. Si
        // no cumple, lanzar una excepción.
        if (!passwordEncoder.verify(user.getPassword(), storedUser.getPassword())) {
            throw new PasswordEncoderExcepcion("Contraseña incorrecta.");
        }

        // crea el token JWT
        return jwtTokenUtil.generateToken(user.getUsername());
    }
}
