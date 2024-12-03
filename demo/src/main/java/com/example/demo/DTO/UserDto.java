package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// Clase que representa el objeto que se recibe en el body de la petición de
// usuario
public class UserDto {
    private Integer id;
    private String username;
    private String userType;
}