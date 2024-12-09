import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Login from "./Pantallas/Login";

import Playlist from "./Pantallas/Playlist";
import MenuPrincipal from "./Pantallas/MenuPrincipal";
import Registro from "./Pantallas/Registro";
import MenuSongs from "./Pantallas/MenuSongs";
import MenuPlayList from "./Pantallas/MenuPlayList";
import CrearPlayList from "./Pantallas/CrearPlayList";
function App() {
  return (
    <Router>
      <div>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/" element={<Login />} />
          <Route path="/registrar" element={<Registro />} />
          <Route path="/playlist/:id" element={<Playlist />} />
          <Route path="/MenuPrincipal" element={<MenuPrincipal />} />
          <Route path="/canciones" element={<MenuSongs />} />
          <Route path="/playlist" element={<MenuPlayList />} />
          <Route path="/crearPlaylist" element={<CrearPlayList />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
