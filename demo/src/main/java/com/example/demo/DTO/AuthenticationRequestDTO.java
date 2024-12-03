package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Clase que representa el objeto que se recibe en el body de la petición de
// autenticación
public class AuthenticationRequestDTO {

    private String username;
    private String password;
    private String userType;
}
