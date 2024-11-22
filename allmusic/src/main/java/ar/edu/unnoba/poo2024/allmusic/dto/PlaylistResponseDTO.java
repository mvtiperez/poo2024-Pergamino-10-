package ar.edu.unnoba.poo2024.allmusic.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistResponseDTO {
    private Long id;
    private String name;
    private String descripcion;
    private List<SongResponseDTO> songs = new ArrayList<>();
    private UserDTO owner;
}
