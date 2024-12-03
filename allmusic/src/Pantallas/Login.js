import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import './Login.css';

function Login() {
  const [usuario, setUsuario] = useState("");
  const [contrasena, setContrasena] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate(); // Hook para redirigir

  const handleSubmit = async (e) => {
    e.preventDefault(); // Evitar recarga de página
    setError(""); // Limpiar errores previos

    try {
      const response = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username: usuario, password: contrasena }), // Enviar datos
      });

      if (response.ok) {
        const data = await response.json();
        const token = data.token;

        // Guardar token en almacenamiento local o sesión
        localStorage.setItem("authToken", token);

        // Redireccionar o mostrar éxito
        alert("Inicio de sesión exitoso");
        navigate('/menuPrincipal');
        console.log("Token:", token);
      } else {
        setError("Credenciales inválidas. Inténtalo de nuevo.");
      }
    } catch (err) {
      console.error("Error al autenticar:", err);
      setError("Hubo un problema con el servidor. Inténtalo más tarde.");
    }
  };

  // Función para redirigir a la página de registro
  const goToRegistro = () => {
    navigate("/registrar");  // Redirige a la ruta de registro
  };

  return (
    <div id="form-ui">
      <form id="form" onSubmit={handleSubmit}>
        <div id="form-body">
          <div id="welcome-lines">
            <div id="welcome-line-1">AllMusic</div>
          </div>
          <div id="input-area">
            <div className="form-inp">
              <input
                placeholder="Usuario"
                type="text"
                value={usuario}
                onChange={(e) => setUsuario(e.target.value)} // Capturar valor
              />
            </div>
            <div className="form-inp">
              <input
                placeholder="Contraseña"
                type="password"
                value={contrasena}
                onChange={(e) => setContrasena(e.target.value)} // Capturar valor
              />
            </div>
          </div>
          <div id="submit-button-cvr">
            <button id="submit-button" type="submit">Iniciar sesión</button>
          </div>
          {error && <p style={{ color: "red", textAlign: "center" }}>{error}</p>}
          <div id="forgot-pass">
            {/* Cambié el <a> por un botón que usa el hook navigate */}
            <button type="button" onClick={goToRegistro} style={{ background: "none", border: "none", color: "#007bff", textDecoration: "underline", cursor: "pointer" }}>
              Registrarse
            </button>
          </div>
        </div>
      </form>
    </div>
  );
}

export default Login;


