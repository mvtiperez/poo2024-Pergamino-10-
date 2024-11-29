package com.example.demo.repositories;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Genre;
import com.example.demo.entities.Song;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SongRepository extends JpaRepository<Song, Long>  {
    List<Song> findByArtist_id(Long artist_id);
    List<Song> findByGenre(Genre genre);
    List<Song> findByArtist_idAndGenre(Long artist_id, Genre genre);
}