import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Helmet } from "react-helmet";
import "./Cancion.css";

function CrearPlayList() {
    const navigate = useNavigate();
    const [activeLink, setActiveLink] = useState(null);
    const [playlistName, setPlaylistName] = useState(""); // Estado para el nombre de la playlist
    const [errorMessage, setErrorMessage] = useState(""); // Estado para manejar errores
    const [successMessage, setSuccessMessage] = useState(""); // Estado para manejar éxito

    const handleNavigation = (path, index) => {
        setActiveLink(index);
        setTimeout(() => {
            navigate(path);
        }, 100);
    };

    const handleCreatePlaylist = async () => {
        if (!playlistName.trim()) {
            setErrorMessage("El nombre de la playlist no puede estar vacío.");
            return;
        }

        try {
            const token = localStorage.getItem("authToken"); // Suponiendo que el token se guarda en localStorage
            const response = await fetch("http://localhost:8080/api/playlist/playlist", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify({ name: playlistName }),
            });

            if (response.ok) {
                setSuccessMessage("Playlist creada exitosamente.");
                setPlaylistName(""); // Reiniciar el formulario
                setErrorMessage("");
            } else {
                throw new Error("No se pudo crear la playlist.");
            }
        } catch (error) {
            setErrorMessage(error.message);
            setSuccessMessage("");
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
                    <h2>Crear Playlist</h2>
                    <div className="form-container">
                        <input
                            type="text"
                            placeholder="Nombre de la Playlist"
                            value={playlistName}
                            onChange={(e) => setPlaylistName(e.target.value)}
                        />
                        <button onClick={handleCreatePlaylist}>Crear</button>
                        {errorMessage && <p className="error">{errorMessage}</p>}
                        {successMessage && <p className="success">{successMessage}</p>}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default CrearPlayList;
