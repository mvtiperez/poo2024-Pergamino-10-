package ar.edu.unnoba.poo2024.allmusic.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorMessage> handle(ResponseStatusException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getReason(),
                ex.getStatusCode().value(),
                request.getRequestURI());
        return new ResponseEntity<>(errorMessage, ex.getStatusCode());
    }

    @ExceptionHandler(PlaylistNoEncontradaException.class)
    public ResponseEntity<String> manejarAlbumNoEncontrado(PlaylistNoEncontradaException ex) {
        // retorna mensaje de la excepción y 404 Not Found
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CancionNoEncontrada.class)
    public ResponseEntity<String> manejarCancionesEncontradas(CancionNoEncontrada ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasswordEncoderExcepcion.class)
    public ResponseEntity<String> manejarConstraseñaIncorrecta(PasswordEncoderExcepcion ex) {
        // retorna mensaje de la excepción y 404 Not Found
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> manejarAuthentication(AuthenticationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
