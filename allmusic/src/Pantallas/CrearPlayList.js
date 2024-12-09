import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Helmet } from "react-helmet";
import axios from "axios";
import "./CrearPlayList.css";

const CrearPlayList = () => {
  const [playlistName, setPlaylistName] = useState("");
  const [message, setMessage] = useState("");
  const [activeLink, setActiveLink] = useState(null);
  const navigate = useNavigate();

  const handleChange = (e) => {
    setPlaylistName(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem("token");
      const response = await axios.post(
        "http://localhost:8080/api/playlist/playlist",
        { name: playlistName },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setMessage("Playlist created successfully");
      navigate("/playlist");
    } catch (error) {
      setMessage("Error creating playlist");
    }
  };

  const handleNavigation = (path, index) => {
    setActiveLink(index);
    navigate(path);
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
                <h3>Menu playlist</h3>
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
          <h2>Crear nueva playlist</h2>
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="playlistName">Nombre de la playlist</label>
              <input
                type="text"
                id="playlistName"
                value={playlistName}
                onChange={handleChange}
                required
              />
            </div>
            <button type="submit">Crear playlist</button>
          </form>
          {message && <p>{message}</p>}
        </div>
      </div>
    </div>
  );
};

export default CrearPlayList;
