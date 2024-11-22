package ar.edu.unnoba.poo2024.allmusic.entities;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Setter 
@Getter 
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
                                                    
@DiscriminatorColumn(name="user_type",
        discriminatorType = DiscriminatorType.STRING)
                                                    
public abstract class User {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK autoincremental
    private Long id;
    @Column(name = "username") 
    private String username;
    @Column(name = "password")
    private String password;

    public abstract boolean canCreateSongs();

}


