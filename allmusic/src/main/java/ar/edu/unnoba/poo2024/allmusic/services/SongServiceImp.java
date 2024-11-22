package ar.edu.unnoba.poo2024.allmusic.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ar.edu.unnoba.poo2024.allmusic.dto.SongCreateUpdateDTO;
import ar.edu.unnoba.poo2024.allmusic.dto.SongResponseDTO;
import ar.edu.unnoba.poo2024.allmusic.entities.Genre;
import ar.edu.unnoba.poo2024.allmusic.entities.MusicArtiesUser;
import ar.edu.unnoba.poo2024.allmusic.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.poo2024.allmusic.entities.Song;
import ar.edu.unnoba.poo2024.allmusic.repositories.SongRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SongServiceImp implements SongService {

    @Autowired
    SongRepository songRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Song> getAll() {
        return songRepository.findAll();
    }

    public List<SongResponseDTO> mapToDtoList(List<Song> songs) {
        return songs
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<SongResponseDTO> getFilterArtistGenre(String artist, Genre genre) {
        try {
            List<Song> songs = songRepository.findByFilter(artist, genre);
            return this.mapToDtoList(songs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public SongResponseDTO getById(Long id) {
        Song song = songRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cancion no encontrado"));
        return this.mapToDto(song);
    }

    public void createSong(SongCreateUpdateDTO dto, User user) {

        if (!user.canCreateSongs())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "El usuario NO puede crear cancion");

        try {
            Song song = new Song();
            song.setNombre(dto.getName());
            song.setGenre(dto.getGenre());
            song.setAuthor((MusicArtiesUser) user);
            songRepository.save(song);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear cancion");
        }

    }

    public void updateSongById(Long id, SongCreateUpdateDTO songCreateUpdateDTO, String username) {
        Song song = songRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cancion no encontrado"));

        if (!song.getAuthor().getUsername().equals(username))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "El usuario no tiene autorización para modificar la cancion");

        try {
            song.setNombre(songCreateUpdateDTO.getName());
            song.setGenre(songCreateUpdateDTO.getGenre());
            songRepository.save(song);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar la cancion");
        }
    }

    public void deleteSongById(Long id, String username) {
        Song song = songRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "cancion no encontrada"));

        if (!song.getAuthor().getUsername().equals(username))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "El usuario no tiene autorización para eliminar la cancion");

        try {
            songRepository.deleteById(song.getId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar la cancion");
        }
    }

    public List<SongResponseDTO> getSongByMe(String username) {
        try {
            List<Song> songs = songRepository.findByAuthor(username);
            return this.mapToDtoList(songs);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error, no se encuentran resultados");
        }
    }

    private SongResponseDTO mapToDto(Song song) {
        SongResponseDTO dto = new SongResponseDTO();
        dto.setId(song.getId());
        dto.setName(song.getNombre());
        dto.setDescription(null);
        dto.setGenre(song.getGenre());

        SongResponseDTO.Artist artist = dto.new Artist();
        artist.setId(song.getAuthor().getId());
        artist.setName(song.getAuthor().getUsername());
        dto.setArtist(artist);
        return dto;
    }
}
