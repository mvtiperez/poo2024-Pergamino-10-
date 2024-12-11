import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';
import "./Registro.css"; // Asegúrate de que la ruta es correcta

function Registro() {
  const [username, setusername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [userType, setUserType] = useState("artist"); // Valor por defecto: "artist"
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleRegistro = async (e) => {
    e.preventDefault();
    setMessage("");

    // Validar que las contraseñas coincidan
    if (password !== confirmPassword) {
      setMessage("Las contraseñas no coinciden");
      return;
    }

    // Configurar el endpoint según el tipo de usuario
    const endpoint =
      userType === "artist"
        ? "http://localhost:8080/api/artist"
        : "http://localhost:8080/api/enthusiast";

    try {
      // Hacer la solicitud POST con fetch
      const response = await fetch(endpoint, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username,
          password,
        }),
      });

      // Comprobar el código de estado de la respuesta
      if (response.status === 201) {
        setMessage("Usuario registrado exitosamente");
        // Redirigir a la página de inicio de sesión o cualquier otra página
        navigate("/"); // Si tienes un endpoint de login
      } else if (response.status === 409) {
        setMessage("Error: El usuario ya existe.");
      } else {
        setMessage("Error desconocido. Inténtalo nuevamente.");
      }
    } catch (error) {
      setMessage("Error de conexión. Inténtalo más tarde.");
    }
  };

  return (
    <div id="form-ui">
      <form id="form" classUsername="Registro-form" onSubmit={handleRegistro}>
        <div id="form-body">
          {/* Título de Bienvenida */}
          <div id="welcome-lines">
            <h1 id="welcome-line-1">Bienvenido</h1>
            <p id="welcome-line-2">Regístrate para comenzar</p>
          </div>

          {/* Nombre */}
          <div id="input-area">
            <div classUsername="form-inp">
              <input
                type="text"
                id="username"
                value={username}
                onChange={(e) => setusername(e.target.value)}
                required
                placeholder="Ingresa tu nombre"
              />
            </div>

            {/* Contraseña */}
            <div classUsername="form-inp">
              <input
                type="password"
                id="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
                placeholder="Ingresa tu contraseña"
              />
            </div>

            {/* Confirmar Contraseña */}
            <div classUsername="form-inp">
              <input
                type="password"
                id="confirmPassword"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                required
                placeholder="Confirma tu contraseña"
              />
            </div>

            {/* Tipo de Usuario */}
            <div classUsername="form-inp">
              <select
                id="userType"
                value={userType}
                onChange={(e) => setUserType(e.target.value)}
              >
                <option value="artist">Artista</option>
                <option value="enthusiast">Entusiasta</option>
              </select>
            </div>
          </div>

          {/* Mensaje */}
          {message && <p classUsername="message">{message}</p>}

          {/* Botón de Registro */}
          <button id="submit-button" type="submit" classUsername="Registro-button">
            Registrarse
          </button>
        </div>

        {/* Decoración */}
        <div id="bar"></div>
      </form>
    </div>
  );
}

export default Registro;
