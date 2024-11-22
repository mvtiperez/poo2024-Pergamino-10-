package ar.edu.unnoba.poo2024.allmusic.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ar.edu.unnoba.poo2024.allmusic.dto.PlaylistCreateUpdateDTO;
import ar.edu.unnoba.poo2024.allmusic.dto.PlaylistResponseDTO;
import ar.edu.unnoba.poo2024.allmusic.entities.User;
import ar.edu.unnoba.poo2024.allmusic.exceptions.PlaylistNoEncontradaException;
import ar.edu.unnoba.poo2024.allmusic.services.AuthorizationService;
import ar.edu.unnoba.poo2024.allmusic.services.PlaylistService;

@RestController
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    AuthorizationService authorizationService;

    @GetMapping("/playlists/{id}")
    public ResponseEntity<?> getPlaylistById(@PathVariable Long id) throws PlaylistNoEncontradaException {
        return ResponseEntity.ok(playlistService.getPlaylistById(id));
    }

    @GetMapping("/playlists")
    public ResponseEntity<?> getAllPlaylists() {
        List<PlaylistResponseDTO> playlists = playlistService.getAllPlaylists();
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("me/playlists")
    public ResponseEntity<?> getPlaylistByMe(@RequestHeader("Authorization") String token) throws Exception {

        if (authorizationService.authorize(token) == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
        }

        User user = authorizationService.authorize(token);
        return ResponseEntity.ok(playlistService.playlistsByMe(user));
    }

    @PostMapping("/playlists")
    public ResponseEntity<?> addPlaylist(@RequestHeader("Authorization") String token,
            @RequestBody PlaylistCreateUpdateDTO playlistCreateUpdateDTO) throws Exception {

        if (authorizationService.authorize(token) == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
        }

        User user = authorizationService.authorize(token);
        playlistService.createPlaylist(playlistCreateUpdateDTO, user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/playlists/{id}/songs")
    public ResponseEntity<?> addSongToPlaylist(@RequestHeader("Authorization") String token,
            @PathVariable Long id, @RequestBody Map<String, Long> body) throws Exception {

        if (authorizationService.authorize(token) == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
        }
        User user = authorizationService.authorize(token);
        Long songId = body.get("song_id");

        playlistService.addSongToPlaylist(id, songId, user.getUsername());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/playlists/{id}")
    public ResponseEntity<?> updatePlaylist(@RequestHeader("Authorization") String token,
            @PathVariable Long id, @RequestBody PlaylistCreateUpdateDTO playlistCreateUpdateDTO) throws Exception {

        if (authorizationService.authorize(token) == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
        }
        User user = authorizationService.authorize(token);
        playlistService.updatePlaylistById(id, playlistCreateUpdateDTO, user.getUsername());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/playlists/{id}")
    public ResponseEntity<?> deletePlaylist(@RequestHeader("Authorization") String token, @PathVariable Long id)
            throws Exception {

        if (authorizationService.authorize(token) == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
        }
        User user = authorizationService.authorize(token);
        playlistService.deletePlaylistById(id, user.getUsername());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/playlists/{playlistId}/songs/{idSong}")
    public ResponseEntity<?> deleteSongFromPlaylist(@RequestHeader("Authorization") String token,
            @PathVariable Long playlistId, @PathVariable Long idSong) throws Exception {

        if (authorizationService.authorize(token) == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
        }

        User user = authorizationService.authorize(token);
        playlistService.deleteSongToPlaylist(playlistId, idSong, user.getUsername());
        return ResponseEntity.ok().build();
    }

}