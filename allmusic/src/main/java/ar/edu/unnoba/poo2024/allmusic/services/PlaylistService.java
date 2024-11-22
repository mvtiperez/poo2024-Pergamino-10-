
package ar.edu.unnoba.poo2024.allmusic.services;

import ar.edu.unnoba.poo2024.allmusic.dto.PlaylistCreateUpdateDTO;
import ar.edu.unnoba.poo2024.allmusic.dto.PlaylistResponseDTO;
import ar.edu.unnoba.poo2024.allmusic.dto.UserDTO;
import ar.edu.unnoba.poo2024.allmusic.entities.Playlist;
import ar.edu.unnoba.poo2024.allmusic.entities.Song;
import ar.edu.unnoba.poo2024.allmusic.entities.User;
import ar.edu.unnoba.poo2024.allmusic.exceptions.CancionNoEncontrada;
import ar.edu.unnoba.poo2024.allmusic.exceptions.PlaylistNoEncontradaException;
import ar.edu.unnoba.poo2024.allmusic.repositories.PlaylistRepository;
import ar.edu.unnoba.poo2024.allmusic.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private SongService songService;

    public void createPlaylist(PlaylistCreateUpdateDTO playlistCreateUpdateDTO, User user) {
        try {
            Playlist playlist = new Playlist();
            playlist.setNombre(playlistCreateUpdateDTO.getNombre());
            List<Song> songs = new ArrayList<>();
            playlist.setSongs(songs);
            playlist.setDescription("");
            playlist.setOwner(user);
            playlistRepository.save(playlist);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public List<PlaylistResponseDTO> getAllPlaylists() {
        List<Playlist> playlists = playlistRepository.findAll();
        return playlists
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PlaylistResponseDTO getPlaylistById(Long id) throws PlaylistNoEncontradaException {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new PlaylistNoEncontradaException("Playlist No Encontrada."));
        return this.mapToDTO(playlist);
    }

    public void updatePlaylistById(Long id, PlaylistCreateUpdateDTO playlistCreateUpdateDTO, String username)
            throws PlaylistNoEncontradaException {

        Playlist playlist = playlistRepository.findById(id).orElseThrow(
                () -> new PlaylistNoEncontradaException("La playlist no fue encontrada!"));

        if (!playlist.getOwner().getUsername().equals(username))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "El usuario no puede actualizar esta playlist.");

        try {
            playlist.setNombre(playlistCreateUpdateDTO.getNombre());
            playlistRepository.save(playlist);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el playlist.");
        }
    }

    public void deletePlaylistById(Long id, String username) throws PlaylistNoEncontradaException {
        Playlist playlist = playlistRepository.findById(id).orElseThrow(
                () -> new PlaylistNoEncontradaException("Playlist no encontrada!"));

        if (!playlist.getOwner().getUsername().equals(username))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "El usuario no puede eliminar esta playlist.");

        try {
            playlistRepository.delete(playlist);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    public void addSongToPlaylist(Long playlistId, Long songId, String username)
            throws PlaylistNoEncontradaException {

        if (songId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El song no puede estar vacío");
        }

        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(
                () -> new PlaylistNoEncontradaException("Playlist no encontrada!"));

        if (!playlist.getOwner().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permisos para modificar esta playlist");
        }

        Song song = songRepository.findById(songId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Canción no encontrada"));

        try {
            playlist.getSongs().add(song);
            playlistRepository.save(playlist);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public void deleteSongToPlaylist(Long playlistId, Long songId, String username)
            throws PlaylistNoEncontradaException {

        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(
                () -> new PlaylistNoEncontradaException("Playlist no encontrada!"));

        if (!playlist.getOwner().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permisos para modificar esta playlist");
        }

        Song songRemove = playlist.getSongs()
                .stream()
                .filter(song -> song.getId().equals(songId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Canción no encontrada en la playlist"));

        try {
            playlist.getSongs().remove(songRemove);
            playlistRepository.save(playlist);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public List<PlaylistResponseDTO> playlistsByMe(User user) {
        List<Playlist> playlists = playlistRepository.findByOwner(user);
        return playlists.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private PlaylistResponseDTO mapToDTO(Playlist playlist) {
        PlaylistResponseDTO playlistDTO = new PlaylistResponseDTO();
        UserDTO userDTO = new UserDTO();
        playlistDTO.setId(playlist.getId());
        playlistDTO.setName(playlist.getNombre());
        playlistDTO.setDescripcion(playlist.getDescription());
        playlistDTO.setSongs(songService.mapToDtoList(playlist.getSongs()));

        userDTO.setId(playlist.getOwner().getId());
        userDTO.setNombre(playlist.getOwner().getUsername());
        playlistDTO.setOwner(userDTO);
        return playlistDTO;
    }

}
