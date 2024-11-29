package com.example.demo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.SongRequestDto;
import com.example.demo.DTO.SongResponseDTO;
import com.example.demo.entities.Genre;
import com.example.demo.entities.MusicArtistUser;
import com.example.demo.entities.Song;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.SongRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongRepository songRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SongResponseDTO createSong(SongRequestDto SongRequestDto) {
        try {
            if (SongRequestDto.getArtist() == null || SongRequestDto.getArtist().isEmpty()) {
                throw new IllegalArgumentException("Artist username must not be null or empty");
            }
            MusicArtistUser artist = userRepository.findArtistByUsername(SongRequestDto.getArtist())
                    .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
            Song song = new Song();
            song.setArtist(artist);
            song.setName(SongRequestDto.getName());
            song.setGenre(Genre.valueOf(SongRequestDto.getGenre().toUpperCase()));
            Song savedSong = songRepository.save(song);
            return modelMapper.map(savedSong, SongResponseDTO.class);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Optional<SongResponseDTO> getSongById(Long songId) {
        return songRepository.findById(songId)
                .map(song -> modelMapper.map(song, SongResponseDTO.class)); // Mapear a DTO si la canción existe.
    }

    @Override
    public List<SongResponseDTO> getAllSongs() {
        List<Song> songs = songRepository.findAll();
        return songs.stream()
                .map(song -> modelMapper.map(song, SongResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public SongResponseDTO updateSong(Long songId, SongRequestDto SongRequestDto) {
        Song songToUpdate = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Cancion no encontrada"));
        songToUpdate.setName(SongRequestDto.getName());
        songToUpdate.setGenre(Genre.valueOf(SongRequestDto.getGenre().toUpperCase()));
        MusicArtistUser artist = userRepository.findArtistByUsername(SongRequestDto.getArtist())
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
        songToUpdate.setArtist(artist);
        Song updatedSong = songRepository.save(songToUpdate);
        return modelMapper.map(updatedSong, SongResponseDTO.class);
    }

    @Override
    public void deleteSong(Long songId) {
        if (!songRepository.existsById(songId)) {
            throw new ResourceNotFoundException("Cancion no encontrada");
        }
        songRepository.deleteById(songId);
    }

    @Override
    public List<SongResponseDTO> getSongsByArtist(String artistName) {
        MusicArtistUser artist = userRepository.findArtistByUsername(artistName)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
        List<Song> songs = songRepository.findByArtist_id(artist.getId());
        return songs.stream()
                .map(song -> modelMapper.map(song, SongResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SongResponseDTO> getSongsByGenre(String genre) {
        List<Song> songs = songRepository.findByGenre(Genre.valueOf(genre.toUpperCase()));
        return songs.stream()
                .map(song -> modelMapper.map(song, SongResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SongResponseDTO> getSongsByArtistAndGenre(String artistName, String genre) {
        MusicArtistUser artist = userRepository.findArtistByUsername(artistName)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
        List<Song> songs = songRepository.findByArtist_idAndGenre(artist.getId(), Genre.valueOf(genre.toUpperCase()));
        return songs.stream()
                .map(song -> modelMapper.map(song, SongResponseDTO.class))
                .collect(Collectors.toList());
    }


}
