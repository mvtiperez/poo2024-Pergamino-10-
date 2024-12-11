import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Helmet } from 'react-helmet';
import './Cancion.css';

function ActualizarCancion() {
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  const [canciones, setCanciones] = useState([]);
  const [selectedSong, setSelectedSong] = useState(null);

  useEffect(() => {
    const fetchCanciones = async () => {
      setLoading(true);
      try {
        const token = localStorage.getItem("authToken");
        if (!token) {
          setError("No estás autenticado.");
          setLoading(false);
          return;
        }

        const response = await fetch("http://localhost:8080/api/song/me-songs", {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
          },
        });

        if (response.ok) {
          const data = await response.json();
          setCanciones(data);
        } else {
          const errorData = await response.json();
          setError(`Error: ${errorData.message || "No se pudieron obtener las canciones."}`);
        }
      } catch (err) {
        setError("Hubo un problema al obtener las canciones.");
      }
      setLoading(false);
    };

    fetchCanciones();
  }, []);

  // Manejar el cambio en los campos del formulario
  const handleChange = (e) => {
    const { name, value } = e.target;
    setSelectedSong({ ...selectedSong, [name]: value });
  };

  // Enviar los datos al backend para actualizar la canción
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem("authToken");
      if (!token) {
        setError("No estás autenticado.");
        return;
      }

      const response = await fetch(`http://localhost:8080/api/song/${selectedSong.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
        },
        body: JSON.stringify({
          name: selectedSong.name,
          genre: selectedSong.genre,
          artist: selectedSong.artist,
        }),
      });

      if (response.ok) {
        navigate("/canciones");
      } else {
        setError("Error al actualizar la canción.");
      }
    } catch (err) {
      setError("Hubo un problema al actualizar la canción.");
    }
  };

  return (
    <div className="top-bar">
      <div className="container">
        <Helmet>
        <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" rel="stylesheet" />
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
          <h2>Seleccionar Canción para Editar</h2>
          {error && <p style={{ color: 'red' }}>{error}</p>}

          {canciones.length === 0 ? (
            <p>No tienes canciones disponibles para editar.</p>
          ) : (
            <ul>
              {canciones.map((song) => (
                <li key={song.id}>
                  <button onClick={() => setSelectedSong(song)}>
                    {song.name} - {song.genre}
                  </button>
                </li>
              ))}
            </ul>
          )}

          {selectedSong && (
            <>
              <h2>Editar Canción</h2>
              <form onSubmit={handleSubmit}>
                <div>
                  <label>Nombre:
                    <input
                      type="text"
                      name="name"
                      value={selectedSong.name}
                      onChange={handleChange}
                      required
                    />
                  </label>
                </div>
                <div>
                  <label>
                    Género:
                    <select name="genre" value={selectedSong.genre} onChange={handleChange} required>
                      <option value="ROCK">ROCK</option>
                      <option value="POP">POP</option>
                      <option value="TECHNO">TECHNO</option>
                      <option value="JAZZ">JAZZ</option>
                      <option value="FOLK">FOLK</option>
                      <option value="CLASSICAL">CLASSICAL</option>
                    </select>
                  </label>
                </div>
                <div>
                  <label>
                    Artista:
                    <input
                      type="text"
                      name="artist"
                      value={selectedSong.artist}
                      onChange={handleChange}
                      required
                    />
                  </label>
                </div>
                <button type="submit">Actualizar Canción</button>
              </form>
            </>
          )}
        </div>
      </div>
    </div>
  );
}

export default ActualizarCancion;
