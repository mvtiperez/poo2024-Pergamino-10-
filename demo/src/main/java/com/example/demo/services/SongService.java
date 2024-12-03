package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import com.example.demo.DTO.SongRequestDto;
import com.example.demo.DTO.SongResponseDTO;

// esta interfaz define los métodos que se pueden usar para interactuar con las canciones
public interface SongService {
    SongResponseDTO createSong(SongRequestDto SongRequestDto);

    Optional<SongResponseDTO> getSongById(Long songId);

    List<SongResponseDTO> getAllSongs();

    SongResponseDTO updateSong(Long songId, SongRequestDto SongRequestDto);

    void deleteSong(Long songId);

    List<SongResponseDTO> getSongsByArtist(String artistName);

    List<SongResponseDTO> getSongsByGenre(String genre);

    List<SongResponseDTO> getSongsByArtistAndGenre(String artistName, String genre);
}
