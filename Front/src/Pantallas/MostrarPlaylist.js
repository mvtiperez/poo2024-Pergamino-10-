import React, { useState,useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Helmet } from "react-helmet";
import './Cancion2.css';
function MostrarPlaylist() {
    const [Playlist, setPlaylist] = useState([]);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();
  
    useEffect(() => {
      const fetchPlaylist = async () => {
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
          const response = await fetch("http://localhost:8080/api/playlist/playlists", {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              "Authorization": `Bearer ${token}`,
            },
          });
  
          console.log("Respuesta de la API:", response);
          if (response.ok) {
            const data = await response.json();
            console.log("Playlist obtenidas:", data); // Verificar los datos obtenidos
            setPlaylist(data);
          } else {
            const errorData = await response.json();
            console.error("Error de la respuesta:", errorData); // Para depurar el error
            setError(`Error: ${errorData.message || "No se pudieron obtener las playlist."}`);
          }
        } catch (err) {
          console.error("Error al obtener las playlist:", err);
          setError("Hubo un problema al obtener las playlist.");
        }
        setLoading(false);
      };
  
      fetchPlaylist();
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
            <h2>Listado de Playlist</h2>
            {loading ? (
              <p>Cargando Playlist...</p>
            ) : (
              <>
                {error && <p style={{ color: 'red' }}>{error}</p>}
                <table>
                  <thead>
                    <tr>
                      <th>Nombre</th>
                      <th>Cantidad de canciones</th>
                    </tr>
                  </thead>
                  <tbody>
                    {Playlist.length > 0 ? (
                        Playlist.map((playlist) => (
                        <tr key={playlist.id}>
                            <td>{playlist.name}</td> {/* Asegúrate de que 'name' es una propiedad válida */}
                            <td>{playlist.songCount}</td> {/* Asegúrate de que 'songCount' es una propiedad válida */}
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
export default MostrarPlaylist;