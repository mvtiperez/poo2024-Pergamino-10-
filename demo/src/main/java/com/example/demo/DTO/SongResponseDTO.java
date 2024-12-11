package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.demo.Entitie.MusicArtistUser;
import com.example.demo.Entitie.Genre;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SongResponseDTO {

    private Long id;
    private String name;
    private Genre genre;
    private MusicArtistUser artist;
}
