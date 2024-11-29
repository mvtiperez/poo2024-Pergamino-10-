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
public class SongResponseDTO {

    private Long id;
    private String name;
    private Genre genre;
    private MusicArtistUser artist;
}
