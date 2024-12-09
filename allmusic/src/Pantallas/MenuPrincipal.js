import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Helmet } from "react-helmet";
import "./MenuPrincipal.css";

function MenuPrincipal() {
  const navigate = useNavigate();
  const [activeLink, setActiveLink] = useState(null);

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
        <div className="main-container"></div>
      </div>
    </div>
  );
}
export default MenuPrincipal;
