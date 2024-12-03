package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Clase que representa el objeto que se recibe en el body de la petición de resumen de playlist
public class PlaylistSummaryDTO {
    private String name;
    private long songCount;
}
