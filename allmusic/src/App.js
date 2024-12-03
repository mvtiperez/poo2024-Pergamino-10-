import React from 'react';
import {BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './Pantallas/Login'; // Importa tu componente Login
import Registro from './Pantallas/Registro';
import MenuPrincipal from './Pantallas/MenuPrincipal';
import MenuPlayList from './Pantallas/MenuPlayList';
import MenuSongs from './Pantallas/MenuSongs'; 

function App() {
  return (
    <div className="App">
      <Router>
        <div>
          <Routes>
            <Route path="/" element={<Login />}/>
            <Route path="/registrar" element={<Registro/>}/>
            <Route path='/menuPrincipal' element={<MenuPrincipal/>}/>
            <Route path='/canciones' element={<MenuSongs/>}/>
            <Route path='/playlist' element={<MenuPlayList/>}/>
          </Routes>
        </div>
      </Router>
    </div>
  );
}

export default App;

