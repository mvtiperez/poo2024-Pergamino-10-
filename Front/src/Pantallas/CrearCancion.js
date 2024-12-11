import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Helmet } from 'react-helmet';
import './Cancion.css';  // Importar el archivo CSS

function CrearCancion() {
    const [name, setName] = useState("");
    const [genre, setGenre] = useState("");
    const [artist, setArtist] = useState("");
    const navigate = useNavigate();
    const [activeLink, setActiveLink] = useState(null);

    const handleSubmit = async (event) => {
        event.preventDefault();
    
        const token = localStorage.getItem("authToken");
        if (!token) {
            console.error("No se encontró el token de autorización.");
            return;
        }
    
        const songData = {
            name,
            genre,
            artist
        };
    
        try {
            const response = await fetch("http://localhost:8080/api/song", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`, // El token debe ser válido
                },
                body: JSON.stringify(songData),
            });
    
            // Verifica si la respuesta tiene contenido
            if (response.ok) {
                const contentType = response.headers.get("content-type");
                if (contentType && contentType.includes("application/json")) {
                    const data = await response.json();
                    console.log("Respuesta del servidor:", data);
                    navigate("/misCanción");
                } else {
                    console.error("Respuesta del servidor no es JSON:", await response.text());
                }
            } else {
                console.error("Error al crear la canción:", response.status, response.statusText);
                const errorData = await response.text();  // Obtener el cuerpo como texto si no es JSON
                console.error("Detalles del error:", errorData);
            }
        } catch (error) {
            console.error("Error al crear la canción:", error);
        }
    };
        
    const handleNavigation = (path, index) => {
        setActiveLink(index);
        setTimeout(() => {
            navigate(path);
        }, 100);
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
                    <Helmet>
                        <title>Crear Canción</title>
                    </Helmet>
                    <h2>Crear Canción</h2>
                    <form onSubmit={handleSubmit}>
                        <label>
                            Nombre:
                            <input
                                type="text"
                                value={name}
                                onChange={(e) => setName(e.target.value)}
                                required
                            />
                        </label>
                        <label>
                            Género:
                            <select value={genre} onChange={(e) => setGenre(e.target.value)} required>
                                <option value="ROCK">ROCK</option>
                                <option value="POP">POP</option>
                                <option value="TECHNO">TECHNO</option>
                                <option value="JAZZ">JAZZ</option>
                                <option value="FOLK">FOLK</option>
                                <option value="CLASSICAL">CLASSICAL</option>
                            </select>
                        </label>
                        <label>
                            Artista:
                            <input
                                type="text"
                                value={artist}
                                onChange={(e) => setArtist(e.target.value)}
                                required
                            />
                        </label>
                        <button type="submit">Crear Canción</button>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default CrearCancion;