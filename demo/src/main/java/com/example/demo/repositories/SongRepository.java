package com.example.demo.repositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Entitie.Genre;
import com.example.demo.Entitie.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Long>  {
    List<Song> findByArtist_id(Long artist_id);
    List<Song> findByGenre(Genre genre);
    List<Song> findByArtist_idAndGenre(Long artist_id, Genre genre);
    Song findByName(String name);
    List<Song> findByNameContainingIgnoreCase(String name); // Método para buscar canciones por nombre
}