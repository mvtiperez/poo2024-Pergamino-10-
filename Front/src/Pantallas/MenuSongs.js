import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Helmet } from 'react-helmet';
import './MenuSongs.css';

function MenuSongs() {
    const navigate = useNavigate();
    const [activeLink, setActiveLink] = useState(null);
    const [userType, setUserType] = useState(''); // 'artist' o 'enthusiast'

    useEffect(() => {
        const storedUserType = localStorage.getItem("userType");
        console.log("Stored UserType:", storedUserType); // Log para depuración
        if (storedUserType) {
            setUserType(storedUserType);
        }
    }, []);
    

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
                    <h2>Elige la acción que deseas realizar</h2>
                    <div className="button-container">
                        <button onClick={() => handleNavigation('/todasCanción')}>Todas las Canciones</button>
                        
                        {userType && userType === 'Artist' && (
                            <>
                                <button onClick={() => handleNavigation('/crearCanción')}>Crear Canción</button>
                                <button onClick={() => handleNavigation('/actualizarCanción')}>Actualizar Canción</button>
                                <button onClick={() => handleNavigation('/eliminarCanción')}>Eliminar Canción</button>
                            </>
                        )}

                        
                        <button onClick={() => handleNavigation('/buscarCanción')}>Buscar Canción</button>
                        
                        {userType === 'Artist' && (
                            <button onClick={() => handleNavigation('/misCanción')}>Mis Canciones</button>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default MenuSongs;
