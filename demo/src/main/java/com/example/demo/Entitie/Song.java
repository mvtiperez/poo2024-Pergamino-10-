package com.example.demo.Entitie;

import com.example.demo.DTO.SongResponseDTO;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
@Table(name = "song")
public class Song {
     
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    @ManyToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name = "artist_id", nullable = false)
    private MusicArtistUser artist;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;

    public SongResponseDTO map(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'map'");
    }

}