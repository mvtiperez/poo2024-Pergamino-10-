import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Registro.css";

function Registro() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [userType, setUserType] = useState("artist");
  const [message, setMessage] = useState("");
  const navigate = useNavigate(); // Hook para redirigir

  const handleRegistro = async (e) => {
    e.preventDefault(); // Evitar recarga de página
    setMessage(""); // Limpiar mensajes previos

    try {
      const response = await fetch(`http://localhost:8080/api/${userType}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }), // Enviar datos
      });

      if (response.ok) {
        setMessage("Registro exitoso");
        navigate("/login");
      } else {
        setMessage("Error en el registro. Inténtalo de nuevo.");
      }
    } catch (err) {
      console.error("Error al registrar:", err);
      setMessage("Hubo un problema con el servidor. Inténtalo más tarde.");
    }
  };

  return (
    <div id="form-ui">
      <form id="form" onSubmit={handleRegistro}>
        <div id="form-body">
          <div id="welcome-lines">
            <div id="welcome-line-1">AllMusic</div>
          </div>
          <div id="input-area">
            <div className="form-inp">
              <input
                placeholder="Usuario"
                type="text"
                value={username}
                onChange={(e) => setUsername(e.target.value)} // Capturar valor
              />
            </div>
            <div className="form-inp">
              <input
                placeholder="Contraseña"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)} // Capturar valor
              />
            </div>
            <div className="form-inp">
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
          {message && <p className="message">{message}</p>}

          {/* Botón de Registro */}
          <button id="submit-button" type="submit" className="Registro-button">
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
