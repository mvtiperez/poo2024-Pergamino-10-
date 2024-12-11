import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Helmet } from 'react-helmet';
import './Cancion.css';

function ActualizarPlaylist() {
  const [playlists, setPlaylists] = useState([]); // Listado de playlists
  const [selectedPlaylistId, setSelectedPlaylistId] = useState(""); // Playlist seleccionada
  const [nombre, setNombre] = useState(""); // Nombre actual o nuevo de la playlist
  const [error, setError] = useState("");
  const [mensaje, setMensaje] = useState("");
  const navigate = useNavigate();

  const token = localStorage.getItem("authToken"); // Token del usuario logueado

  useEffect(() => {
    // Cargar las playlists propias del usuario
    const fetchPlaylists = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/playlist/me/playlists", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (response.ok) {
          const data = await response.json();
          setPlaylists(data);
        } else {
          setError("No se pudieron cargar las playlists. Intenta más tarde.");
        }
      } catch (error) {
        console.error("Error al cargar playlists:", error);
        setError("Hubo un problema al cargar las playlists.");
      }
    };

    fetchPlaylists();
  }, [token]);

  const handlePlaylistChange = (e) => {
    const playlistId = e.target.value;
    setSelectedPlaylistId(playlistId);

    // Cargar el nombre de la playlist seleccionada
    const selectedPlaylist = playlists.find((playlist) => playlist.id === parseInt(playlistId));
    setNombre(selectedPlaylist ? selectedPlaylist.name : "");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setMensaje("");

    if (!selectedPlaylistId) {
      setError("Debes seleccionar una playlist.");
      return;
    }

    if (!nombre.trim()) {
      setError("El nombre de la playlist no puede estar vacío.");
      return;
    }

    try {
      const response = await fetch(`http://localhost:8080/api/playlist/${selectedPlaylistId}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ name: nombre }),
      });

      if (response.ok) {
        setMensaje("Playlist actualizada exitosamente.");
        setTimeout(() => navigate('/playlist'), 2000); // Redirige después de 2 segundos
      } else if (response.status === 403) {
        setError("No tienes permisos para actualizar esta playlist.");
      } else {
        setError("Error al actualizar la playlist. Intenta de nuevo.");
      }
    } catch (error) {
      console.error("Error al actualizar la playlist:", error);
      setError("Hubo un problema con el servidor. Intenta más tarde.");
    }
  };

  return (
    <div className="top-bar">
      <div className="container">
        <Helmet>
          <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" />
        </Helmet>
        <aside>
          <div className="toggle">
            <h2>AllMusic</h2>
          </div>
          <div className="sidebar">
            <div>
              <a onClick={() => navigate('/menuPrincipal')}>
                <span className="material-icons">home</span>
                <h3>Menu principal</h3>
              </a>
              <a onClick={() => navigate('/playlist')}>
                <span className="material-icons">library_music</span>
                <h3>Menu Playlist</h3>
              </a>
              <a onClick={() => navigate('/canciones')}>
                <span className="material-icons">music_note</span>
                <h3>Menu canciones</h3>
              </a>
              <a onClick={() => navigate('/')}>
                <span className="material-icons">logout</span>
                <h3>Cerrar sesión</h3>
              </a>
            </div>
          </div>
        </aside>

        <div className="main-container">
          <h2>Actualizar Playlist</h2>

          <form onSubmit={handleSubmit}>
            <div>
              <label>Selecciona una playlist:</label>
              <select value={selectedPlaylistId} onChange={handlePlaylistChange}>
                <option value="">-- Selecciona una playlist --</option>
                {playlists.map((playlist) => (
                  <option key={playlist.id} value={playlist.id}>
                    {playlist.name}
                  </option>
                ))}
              </select>
            </div>

            {selectedPlaylistId && (
              <div>
                <label>Nuevo nombre:</label>
                <input
                  type="text"
                  value={nombre}
                  onChange={(e) => setNombre(e.target.value)}
                />
              </div>
            )}

            <button type="submit" disabled={!selectedPlaylistId}>Actualizar</button>
          </form>

          {error && <p style={{ color: "red" }}>{error}</p>}
          {mensaje && <p style={{ color: "green" }}>{mensaje}</p>}
        </div>
      </div>
    </div>
  );
}

export default ActualizarPlaylist;
