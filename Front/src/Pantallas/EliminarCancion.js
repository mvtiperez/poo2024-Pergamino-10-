import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Helmet } from "react-helmet";
import "./Cancion2.css";

function EliminarCancion() {
  const [activeLink, setActiveLink] = useState(null);
  const [searchQuery, setSearchQuery] = useState(""); // Búsqueda ingresada por el usuario
  const [song, setSong] = useState(null); // Datos de la canción obtenida
  const [error, setError] = useState(null); // Manejo de errores
  const [message, setMessage] = useState(null); // Mensaje de éxito
  const navigate = useNavigate();

  const handleNavigation = (path, index) => {
    setActiveLink(index);
    setTimeout(() => {
      navigate(path);
    }, 100);
  };

  const fetchSong = async () => {
    setError(null);
    setSong(null);
    setMessage(null);

    if (!searchQuery) {
      setError("Por favor, ingresa un ID o nombre de la canción.");
      return;
    }

    const token = localStorage.getItem("authToken"); // Asegúrate de que el token JWT esté almacenado en localStorage
    if (!token) {
      setError("No estás autenticado. Por favor inicia sesión.");
      return;
    }

    try {
      const isNumeric = !isNaN(searchQuery);
      const url = isNumeric
        ? `http://localhost:8080/api/song/${searchQuery}`
        : `http://localhost:8080/api/song/search?name=${encodeURIComponent(searchQuery)}`;

      const response = await fetch(url, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });

      if (response.ok) {
        const data = await response.json();
        setSong(isNumeric ? data : data[0] || null);
        if (!data || (Array.isArray(data) && data.length === 0)) {
          setError("No se encontraron canciones.");
        }
      } else {
        setError("Error al buscar la canción. Verifica el ID o nombre.");
      }
    } catch (error) {
      setError("Error en la conexión con el servidor.");
      console.error(error);
    }
  };

  const deleteSong = async (id) => {
    const username = localStorage.getItem("usuario"); // Obtener el usuario logueado
    const token = localStorage.getItem("authToken");
    
    if (!token) {
      setError("No estás autenticado. Por favor inicia sesión.");
      return;
    }
  
    if (!song || !song.artist || song.artist.username !== username) {
      setError("No tienes permiso para eliminar esta canción.");
      return;
    }
  
    try {
      const response = await fetch(`http://localhost:8080/api/song/${id}`, {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
  
      if (response.status === 204) {
        setMessage("Canción eliminada exitosamente.");
        setSong(null); // Limpia la canción eliminada del estado
      } else {
        setError("Error al eliminar la canción. Verifica tus permisos.");
      }
    } catch (error) {
      setError("Error en la conexión con el servidor.");
      console.error(error);
    }
  };
  

  return (
    <div className="top-bar">
      <div className="container">
        <Helmet>
          <link
            href="https://fonts.googleapis.com/icon?family=Material+Icons"
            rel="stylesheet"
          />
        </Helmet>
        <aside>
          <div className="toggle">
            <h2>AllMusic</h2>
          </div>
          <div className="sidebar">
            <div>
              <a
                className={activeLink === 0 ? "active clicked" : ""}
                onClick={() => handleNavigation("/menuPrincipal", 0)}
              >
                <span className="material-icons">home</span>
                <h3>Menu principal</h3>
              </a>
              <a
                className={activeLink === 1 ? "active clicked" : ""}
                onClick={() => handleNavigation("/playlist", 1)}
              >
                <span className="material-icons">library_music</span>
                <h3>Menu Playlist</h3>
              </a>
              <a
                className={activeLink === 2 ? "active clicked" : ""}
                onClick={() => handleNavigation("/canciones", 2)}
              >
                <span className="material-icons">music_note</span>
                <h3>Menu canciones</h3>
              </a>
              <a
                className={activeLink === 3 ? "active clicked" : ""}
                onClick={() => handleNavigation("/", 3)}
              >
                <span className="material-icons">logout</span>
                <h3>Cerrar sesión</h3>
              </a>
            </div>
          </div>
        </aside>
        <div className="main-container">
          <h2>Buscar canción para eliminar</h2>
          <div className="search-bar">
            <input
              type="text"
              placeholder="Buscar canción por ID o Nombre"
              className="search-input"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
            />
            <button className="search-button" onClick={fetchSong}>
              <span className="material-icons">search</span>
            </button>
          </div>
          {error && <p className="error">{error}</p>}
          {message && <p className="success">{message}</p>}
          {song && (
            <table className="song-table">
              <thead>
                <tr>
                  <th>Nombre</th>
                  <th>Género</th>
                  <th>Artista</th>
                  <th>Acción</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>{song.name}</td>
                  <td>{song.genre}</td>
                  <td>{song.artist.username}</td>
                  <td>
                    <button
                      className="delete-button"
                      onClick={() => deleteSong(song.id)}
                    >
                      Eliminar canción
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          )}
        </div>
      </div>
    </div>
  );
}

export default EliminarCancion;
