import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { Helmet } from 'react-helmet';

function AgregarCancionePlaylist() {
    const [Playlist, setPlaylist] = useState([]);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);
    const [songId, setSongId] = useState(null);  // Para almacenar el id de la canción seleccionada
    const navigate = useNavigate();
    const [activeLink, setActiveLink] = useState(null);
    
    // Obtén el id de la canción desde la URL
    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const songIdFromUrl = searchParams.get("id");

    useEffect(() => {
      if (songIdFromUrl) {
        setSongId(songIdFromUrl);
      }
      
      const fetchPlaylist = async () => {
        setLoading(true);
        try {
          const token = localStorage.getItem("authToken");
          if (!token) {
            setError("No estás autenticado.");
            setLoading(false);
            return;
          }

          const response = await fetch("http://localhost:8080/api/playlist/me/playlists", {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              "Authorization": `Bearer ${token}`,
            },
          });

          if (response.ok) {
            const data = await response.json();
            setPlaylist(data);
          } else {
            const errorData = await response.json();
            setError(`Error: ${errorData.message || "No se pudieron obtener las playlist."}`);
          }
        } catch (err) {
          setError("Hubo un problema al obtener las playlist.");
        }
        setLoading(false);
      };

      fetchPlaylist();
    }, [songIdFromUrl]);

    const handleNavigation = (path, index) => {
        setActiveLink(index);
        setTimeout(() => {
            navigate(path);
        }, 100);
    };

    const handleAddSongToPlaylist = (playlistId) => {
      const token = localStorage.getItem("authToken");
      if (!token || !songId) {
        setError("No hay token o canción seleccionada.");
        return;
      }

      const addSongDTO = { songID: songId };

      fetch(`http://localhost:8080/api/playlist/${playlistId}/song`, {
        method: "POST",
        headers: {
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify(addSongDTO),
      })
      .then((response) => {
        if (response.ok) {
          alert("Canción agregada a la playlist");
        } else {
          setError("Hubo un error al agregar la canción.");
        }
      })
      .catch((err) => {
        setError("Error de red o al procesar la solicitud.");
      });
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
                            <a
                                className={activeLink === 0 ? 'active clicked' : ''}
                                onClick={() => handleNavigation('/menuPrincipal', 0)}
                            >
                                <span className="material-icons">home</span>
                                <h3>Menu principal</h3>
                            </a>
                            <a
                                className={activeLink === 1 ? 'active clicked' : ''}
                                onClick={() => handleNavigation('/playlist', 1)}
                            >
                                <span className="material-icons">library_music</span>
                                <h3>Menu Playlist</h3>
                            </a>
                            <a
                                className={activeLink === 2 ? 'active clicked' : ''}
                                onClick={() => handleNavigation('/canciones', 2)}
                            >
                                <span className="material-icons">music_note</span>
                                <h3>Menu canciones</h3>
                            </a>
                            <a
                                className={activeLink === 3 ? 'active clicked' : ''}
                                onClick={() => handleNavigation('/', 3)}
                            >
                                <span className="material-icons">logout</span>
                                <h3>Cerrar sesión</h3>
                            </a>
                        </div>
                    </div>
                </aside>
                <div className="main-container">
                <h2>Listado de Mis Playlist</h2>
            {loading ? (
              <p>Cargando Playlist...</p>
            ) : (
              <>
                {error && <p style={{ color: 'red' }}>{error}</p>}
                <table>
                  <thead>
                    <tr>
                      <th>Nombre</th>
                      <th>Acción</th>
                    </tr>
                  </thead>
                  <tbody>
                    {Playlist.length > 0 ? (
                        Playlist.map((playlist) => (
                        <tr key={playlist.id}>
                            <td>{playlist.name}</td>
                            <td>
                                <button
                                  className="add-to-playlist-button"
                                  onClick={() => handleAddSongToPlaylist(playlist.id)}
                                >
                                    Agregar a Playlist
                                </button>
                            </td>
                        </tr>
                        ))
                    ) : (
                        <tr>
                        <td colSpan="3">No hay Playlist disponibles.</td>
                        </tr>
                    )}
                    </tbody>
                </table>
              </>
            )}
                </div>
            </div>
        </div>
    );
}

export default AgregarCancionePlaylist;
