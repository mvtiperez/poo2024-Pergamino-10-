package com.example.demo.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Enthusiast")
public class MusicEnthusiastUser extends User {

    public MusicEnthusiastUser() {
        super();
    }

    public MusicEnthusiastUser(String username, String password) {
        super(username, password);
    }

    @Override
    public boolean canCreateSongs() {
        return false;
    }
}

