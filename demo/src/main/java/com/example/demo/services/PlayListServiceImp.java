package com.example.demo.services;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DTO.PlaylistResponseDTO;
import com.example.demo.DTO.PlaylistSummaryDTO;
import com.example.demo.Entitie.PlayList;
import com.example.demo.Entitie.Song;
import com.example.demo.Entitie.User;
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
    public void addSongToPlaylist(Long playlistId, User owner, Long songId) {
        PlayList playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
        if (!playlist.getOwner().getId().equals(owner.getId())){
            throw new ResourceNotFoundException("No es el dueño de la playlist");}
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found"));
        playlist.getSongs().add(song);
        playlistRepository.save(playlist);
    }

    @Override
    public List<PlaylistResponseDTO> getMyPlaylists(User owner) {
        List<PlayList> playlists = playlistRepository.findByOwner_id(owner.getId());
        return playlists.stream()
                .map(playlist -> modelMapper.map(playlist, PlaylistResponseDTO.class))
                .collect(Collectors.toList());
    }
    @Override
    public List<PlaylistResponseDTO> searchPlayListByName(String name){
        List<PlayList> playLists= playlistRepository.findByNameContainingIgnoreCase(name);
        return playLists.stream()
        .map(playList -> modelMapper.map(playList,PlaylistResponseDTO.class))
        .collect(Collectors.toList());
    }
}
