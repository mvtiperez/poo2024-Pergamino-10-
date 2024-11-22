package ar.edu.unnoba.poo2024.allmusic.util;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtTokenUtil {
    private static final String TOKEN_SECRET = "SRFFewe3223423t65ndfdsfdsg";
    private static final Algorithm algorithm = Algorithm.HMAC512(TOKEN_SECRET);
    private String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String generateToken(String subject) {
        try {
            // expira en 10 días a partir de la fecha actual
            Date expirar = new Date(System.currentTimeMillis() + 864000000);
            setSubject(subject);
            // genera el token JWT
            String token = JWT.create()
                    .withIssuer(subject) // aca el "subject" es el username configurado
                    .withExpiresAt(expirar)
                    .sign(algorithm);
            return "Bearer " + token; // Retorna el token JWT resultante anteponiendo “Bearer“ al String resultante.
        } catch (JWTCreationException exception) {
            return exception.getMessage();
        }
    }

    public boolean verify(String token) {
        try {
            // algoritmo HMAC 512
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(getSubject())
                    // Este getSubject() debería devolver el mismo "subject" usado al generar el
                    // token
                    .build();
            verifier.verify(token.replace("Bearer ", ""));
            return true; // e retornar true el si el token es válido
        } catch (JWTVerificationException jwtVerificationException) {
            return false;
        }
    }

    public String getSubject(String token) {
        try {
            // Decodifica el token para obtener el subject
            DecodedJWT decodedJWT = JWT.decode(token.replace("Bearer ", ""));
            return decodedJWT.getIssuer(); // Retorna el username como subject
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

}
