package com.example.demo.DTO;

import java.util.List;

import com.example.demo.entities.Song;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistResponseDTO {
    private List<Song> songs;
    private Long id;
    private String name;
}

