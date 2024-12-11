import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Helmet } from 'react-helmet';
import './Cancion2.css';

function MostrarCanciones() {
  const [canciones, setCanciones] = useState([]);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchCanciones = async () => {
      setLoading(true);
      try {
        const token = localStorage.getItem("authToken");
        console.log("Token:", token); // Verificar que el token está presente
        if (!token) {
          setError("No estás autenticado.");
          setLoading(false);
          return;
        }

        console.log("Haciendo solicitud a la API...");
        const response = await fetch("http://localhost:8080/api/song/songs", {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
          },
        });

        console.log("Respuesta de la API:", response);
        if (response.ok) {
          const data = await response.json();
          console.log("Canciones obtenidas:", data); // Verificar los datos obtenidos
          setCanciones(data);
        } else {
          const errorData = await response.json();
          console.error("Error de la respuesta:", errorData); // Para depurar el error
          setError(`Error: ${errorData.message || "No se pudieron obtener las canciones."}`);
        }
      } catch (err) {
        console.error("Error al obtener las canciones:", err);
        setError("Hubo un problema al obtener las canciones.");
      }
      setLoading(false);
    };

    fetchCanciones();
  }, []);

  const handleNavigation = (path) => {
    navigate(path);
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
              <a onClick={() => handleNavigation('/menuPrincipal')}>
                <span className="material-icons">home</span>
                <h3>Menu principal</h3>
              </a>
              <a onClick={() => handleNavigation('/playlist')}>
                <span className="material-icons">library_music</span>
                <h3>Menu Playlist</h3>
              </a>
              <a onClick={() => handleNavigation('/canciones')}>
                <span className="material-icons">music_note</span>
                <h3>Menu canciones</h3>
              </a>
              <a onClick={() => handleNavigation('/')}>
                <span className="material-icons">logout</span>
                <h3>Cerrar sesión</h3>
              </a>
            </div>
          </div>
        </aside>

        <div className="main-container">
          <h2>Listado de Canciones</h2>
          {loading ? (
            <p>Cargando canciones...</p>
          ) : (
            <>
              {error && <p style={{ color: 'red' }}>{error}</p>}
              <table>
                <thead>
                  <tr>
                    <th>Nombre</th>
                    <th>Género</th>
                    <th>Artista</th>
                  </tr>
                </thead>
                <tbody>
                    {canciones.length > 0 ? (
                        canciones.map((cancion) => (
                        <tr key={cancion.id}>
                            <td>{cancion.name}</td>
                            <td>{cancion.genre}</td>
                            <td>{cancion.artist.username}</td> {/* Asegúrate de que los campos sean correctos */}
                        </tr>
                        ))
                    ) : (
                        <tr>
                        <td colSpan="3">No hay canciones disponibles.</td>
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

export default MostrarCanciones;

