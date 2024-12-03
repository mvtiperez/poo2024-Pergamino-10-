package com.example.demo.DTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Clase que representa el objeto que se recibe en el body de la petición de creación de usuario
public class CreateUserRequestDTO {
    private String username;
    private String password;
}

