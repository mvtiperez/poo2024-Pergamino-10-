package ar.edu.unnoba.poo2024.allmusic.services;

import java.util.List;
import java.util.Optional;

import ar.edu.unnoba.poo2024.allmusic.dto.SongCreateUpdateDTO;
import ar.edu.unnoba.poo2024.allmusic.dto.SongResponseDTO;
import ar.edu.unnoba.poo2024.allmusic.entities.Genre;
import ar.edu.unnoba.poo2024.allmusic.entities.Song;

import ar.edu.unnoba.poo2024.allmusic.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface SongService {


    List<Song> getAll();

    List<SongResponseDTO> getSongByMe(String username);

    List<SongResponseDTO> getFilterArtistGenre(String artist, Genre genre);

    List<SongResponseDTO> mapToDtoList(List<Song> songs );

    SongResponseDTO getById(Long id);

    void updateSongById(Long id, SongCreateUpdateDTO songCreateUpdateDTO, String username);

    void deleteSongById(Long id, String username);

    void createSong(SongCreateUpdateDTO dto, User user);

}
