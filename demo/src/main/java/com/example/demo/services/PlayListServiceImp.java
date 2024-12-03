package com.example.demo.services;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DTO.PlaylistResponseDTO;
import com.example.demo.DTO.PlaylistSummaryDTO;
import com.example.demo.entities.PlayList;
import com.example.demo.entities.Song;
import com.example.demo.entities.User;
import com.example.demo.repositories.PlayListRepository;
import com.example.demo.repositories.SongRepository;
import com.example.demo.exceptions.ResourceNotFoundException;

@Service
public class PlayListServiceImp implements PlayListService {

    @Autowired
    private PlayListRepository playlistRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PlaylistSummaryDTO> getPlaylistsWithSongCount() {
        return playlistRepository.findPlaylistsWithSongCount();
    }

    @Override
    public void createPlaylist(String playlistName, User owner) {
        PlayList playlist = new PlayList();
        playlist.setOwner(owner);
        playlist.setName(playlistName);
        playlistRepository.save(playlist);
    }

    @Override
    public PlaylistResponseDTO getPlaylistById(Long playlistId) {
        PlayList playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
        return modelMapper.map(playlist, PlaylistResponseDTO.class);
    }

    @Override
    // implementa el método updatePlaylistName
    // este método actualiza el nombre de una lista de reproducción
    // lanza una excepción ResourceNotFoundException si la lista de reproducción no
    // existe
    public void updatePlaylistName(Long playlistId, User owner, String playlistName) {
        PlayList playlistToUpdate = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
        if (!playlistToUpdate.getOwner().getId().equals(owner.getId())) {
            throw new ResourceNotFoundException("No es el dueño de la playlist");
        }
        playlistToUpdate.setName(playlistName);
        playlistRepository.save(playlistToUpdate);
    }

    @Override
    // implementa el método deletePlaylist
    // este método elimina una lista de reproducción
    // lanza una excepción ResourceNotFoundException si la lista de reproducción no
    // existe
    // lanza una excepción ResourceNotFoundException si el usuario no es el dueño de
    // la lista de reproducción
    public void deletePlaylist(Long playlistId, User owner) {
        PlayList playlistToDelete = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
        if (!playlistToDelete.getOwner().getId().equals(owner.getId())) {
            throw new ResourceNotFoundException("No es el dueño de la playlist");
        }
        playlistToDelete.getSongs().clear();
        playlistRepository.save(playlistToDelete);

        playlistRepository.deleteById(playlistId);
    }

    @Override
    // implementa el método addSongToPlaylist
    // este método agrega una canción a una lista de reproducción
    // lanza una excepción ResourceNotFoundException si la lista de reproducción no
    // existe
    public void addSongToPlaylist(Long playlistId, User owner, Long songId) {
        PlayList playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
        if (!playlist.getOwner().getId().equals(owner.getId())) {
            throw new ResourceNotFoundException("No es el dueño de la playlist");
        }
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found"));
        playlist.getSongs().add(song);
        playlistRepository.save(playlist);
    }

    @Override
    // implementa el método getMyPlaylists
    // Este método debe devolver una lista de PlaylistResponseDTO con las listas de
    // reproducción del usuario
    public List<PlaylistResponseDTO> getMyPlaylists(User owner) {
        List<PlayList> playlists = playlistRepository.findByOwner_id(owner.getId());
        return playlists.stream()
                .map(playlist -> modelMapper.map(playlist, PlaylistResponseDTO.class))
                .collect(Collectors.toList());
    }
}
