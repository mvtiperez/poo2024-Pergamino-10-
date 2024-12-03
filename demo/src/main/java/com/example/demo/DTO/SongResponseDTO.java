package com.example.demo.DTO;

import com.example.demo.entities.Genre;
import com.example.demo.entities.MusicArtistUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Clase que representa el objeto que se recibe en el body de la petición de
// respuesta de canción
public class SongResponseDTO {

    private Long id;
    private String name;
    private Genre genre;
    private MusicArtistUser artist;
}
