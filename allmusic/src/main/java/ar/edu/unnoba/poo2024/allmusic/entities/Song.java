package ar.edu.unnoba.poo2024.allmusic.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

//    @ManyToOne
//    @JoinColumn(name = "music_arties_id", referencedColumnName = "id")  // 'user_id' será la FK en la tabla 'Playlist' que apunta a 'User'
//    private MusicArtiesUser author;

    @ManyToOne
//            (cascade = CascadeType.ALL) Al eliminar la canción, también elimina al usuario.
    @JoinColumn(name = "author_id", nullable = false)
    private MusicArtiesUser author;

}
