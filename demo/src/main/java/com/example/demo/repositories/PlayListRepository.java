package com.example.demo.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.DTO.PlaylistSummaryDTO;
import com.example.demo.entities.PlayList;

@Repository
public interface PlayListRepository extends JpaRepository<PlayList, Long> {
    @Query("SELECT new com.example.demo.DTO.PlaylistSummaryDTO(p.name, COUNT(s.id)) " +
           "FROM PlayList p " +
           "LEFT JOIN p.songs s " +
           "GROUP BY p.id, p.name")
    List<PlaylistSummaryDTO> findPlaylistsWithSongCount();

    List<PlayList> findByOwner_id(Long owner_id);
}