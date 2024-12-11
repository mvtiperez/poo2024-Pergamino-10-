package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Entitie.MusicArtistUser;
import com.example.demo.Entitie.User;
import org.springframework.lang.NonNull;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM MusicArtistUser u WHERE u.username = :username")
    Optional<MusicArtistUser> findArtistByUsername(@Param("username") String username);

    Optional<User> findByUsername(String username);
    
    @NonNull
    Optional<User> findById(@NonNull Long id);
}
