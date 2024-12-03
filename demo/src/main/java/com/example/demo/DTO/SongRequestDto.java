package com.example.demo.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Clase que representa el objeto que se recibe en el body de la petición de
// agregar canción
public class SongRequestDto {
    private String name;
    private String genre;
    private String artist;
}
