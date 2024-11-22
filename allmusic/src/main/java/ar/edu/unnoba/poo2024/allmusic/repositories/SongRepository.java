package ar.edu.unnoba.poo2024.allmusic.repositories;

import ar.edu.unnoba.poo2024.allmusic.entities.Genre;
import ar.edu.unnoba.poo2024.allmusic.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    @Query("SELECT s FROM Song s WHERE s.author.username = :artistName AND s.genre = :genre")
    List<Song> findByFilter(@Param("artistName") String artistName, @Param("genre") Genre genre);

    @Query("SELECT s FROM Song s WHERE s.author.username = :artistName")
    List<Song> findByAuthor(@Param("artistName") String artistName);
}
