import React from 'react';
import {BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './Pantallas/Login'; // Importa tu componente Login
import Registro from './Pantallas/Registro';
import MenuPrincipal from './Pantallas/MenuPrincipal';
import MenuPlayList from './Pantallas/MenuPlayList';
import MenuSongs from './Pantallas/MenuSongs'; 
import CrearCancion from './Pantallas/CrearCancion';
import MostrarCanciones from './Pantallas/MostrarCanciones';
import MisCanciones from './Pantallas/MisCanciones';
import ActualizarCancion from './Pantallas/ActualizarCancion';
import BuscarCancion from './Pantallas/BuscarCancion';
import EliminarCancion from './Pantallas/EliminarCancion';
import AgregarCancionePlaylist from './Pantallas/AgregarCancionPlaylist';
import CrearPlaylist from './Pantallas/CrearPlayList';
import MostrarPlaylist from './Pantallas/MostrarPlaylist';
import MisPlaylist from './Pantallas/MisPlaylist';
import ActualizarPlaylist from './Pantallas/ActualizarPlaylist';
import EliminarPlaylist from './Pantallas/EliminarPlaylist';
import BuscarPlaylist  from './Pantallas/BuscarPlaylist';


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
            <Route path='/crearCanción' element={<CrearCancion/>}/>
            <Route path='/playlist' element={<MenuPlayList/>}/>
            <Route path='/todasCanción' element={<MostrarCanciones/>}/>
            <Route path='/misCanción' element={<MisCanciones/>}/>
            <Route path='/actualizarCanción' element={<ActualizarCancion/>}/>
            <Route path='/buscarCanción' element={<BuscarCancion/>}/>
            <Route path='/agregarAPlaylist' element={<AgregarCancionePlaylist/>}/>
            <Route path='/eliminarCanción' element={<EliminarCancion/>}/>
            <Route path='/crearPlaylist' element={<CrearPlaylist/>}/>
            <Route path='/todasPlaylist' element={<MostrarPlaylist/>}/>
            <Route path='/misPlaylist' element={<MisPlaylist/>}/>
            <Route path='/actualizarPlaylist' element={<ActualizarPlaylist/>}/>
            <Route path='/eliminarPlaylist' element={<EliminarPlaylist/>}/>
            <Route path='/buscarPlaylist' element={<BuscarPlaylist/>}/>
          </Routes>
        </div>
      </Router>
    </div>
  );
}

export default App;

