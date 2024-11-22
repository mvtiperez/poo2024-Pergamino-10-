package ar.edu.unnoba.poo2024.allmusic.services;

import ar.edu.unnoba.poo2024.allmusic.exceptions.UserPrincipalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.auth0.jwt.exceptions.JWTVerificationException;
import ar.edu.unnoba.poo2024.allmusic.entities.User;
import ar.edu.unnoba.poo2024.allmusic.util.JwtTokenUtil;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthorizationServiceImp implements AuthorizationService {
    
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserService userService;
    
    public User authorize(String token) throws Exception {
        try {
            jwtTokenUtil.verify(token);
            String subject = jwtTokenUtil.getSubject(token);
            User usuario = userService.findByUsername(subject);
            if (usuario == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario no fue encontrado. Créalo");
            }
            return usuario;

        } catch (JWTVerificationException jwtVerificationException) {
            throw new JWTVerificationException("Token inválido", jwtVerificationException);
        }
    }
}
