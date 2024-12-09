import React, { useState, useEffect } from "react";
import { getPlaylistById } from "../services/playlistService";

const Playlist = ({ playlistId }) => {
  const [playlist, setPlaylist] = useState(null);

  useEffect(() => {
    const fetchPlaylist = async () => {
      try {
        const data = await getPlaylistById(playlistId);
        setPlaylist(data);
      } catch (error) {
        console.error("Error fetching playlist", error);
      }
    };

    fetchPlaylist();
  }, [playlistId]);

  if (!playlist) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h2>{playlist.name}</h2>
      <ul>
        {playlist.songs.map((song) => (
          <li key={song.id}>
            {song.name} by {song.artist.username}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Playlist;
