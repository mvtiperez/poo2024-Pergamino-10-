package com.example.demo.DTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// Clase que representa el objeto que se recibe en el body de la petición de creación y actualización de playlist
public class CreateAndUpdatePlaylistRequestDTO {
    private String name;
}
