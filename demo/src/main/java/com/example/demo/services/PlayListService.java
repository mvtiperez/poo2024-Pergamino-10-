package com.example.demo.services;


import com.example.demo.DTO.PlaylistResponseDTO;
import com.example.demo.DTO.PlaylistSummaryDTO;
import com.example.demo.Entitie.User;
import java.util.List;


public interface PlayListService {
    List<PlaylistSummaryDTO> getPlaylistsWithSongCount();
    void createPlaylist(String playlistName, User owner);
    PlaylistResponseDTO getPlaylistById(Long playlistId);
    void updatePlaylistName(Long playlistId, User owner, String playlistName);
    void deletePlaylist(Long playlistId, User owner);
    void addSongToPlaylist(Long playlistId, User owner, Long songId);
    List<PlaylistResponseDTO> getMyPlaylists(User owner);
    List<PlaylistResponseDTO>searchPlayListByName(String name);
}
