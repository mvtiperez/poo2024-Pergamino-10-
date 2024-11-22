package ar.edu.unnoba.poo2024.allmusic.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Setter
@Getter
@DiscriminatorValue("Artist")
public class MusicArtiesUser extends User{

    public boolean canCreateSongs(){
        return true;
    }

}
