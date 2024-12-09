import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../services/authService";
import "./Login.css";

const Login = () => {
  const [credentials, setCredentials] = useState({
    username: "",
    password: "",
  });
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCredentials({ ...credentials, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const data = await login(credentials);
      setMessage("Login successful");
      // Guardar el token en el almacenamiento local o en el estado de la aplicación
      // Redirigir al usuario a la página de inicio o panel de control
      navigate("/MenuPrincipal"); // Cambia '/dashboard' por la ruta que desees
    } catch (error) {
      setMessage("Invalid credentials");
    }
  };
  // Función para redirigir a la página de registro
  const goToRegistro = () => {
    navigate("/registrar"); // Redirige a la ruta de registro
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
                name="username"
                value={credentials.username}
                onChange={handleChange} // Capturar valor
              />
            </div>
            <div className="form-inp">
              <input
                placeholder="Contraseña"
                type="password"
                name="password"
                value={credentials.password}
                onChange={handleChange} // Capturar valor
              />
            </div>
            <div id="submit-button-cvr">
              <button id="submit-button" type="submit">
                Iniciar sesión
              </button>
            </div>
          </div>
          {message && <p>{message}</p>}
          <div id="forgot-pass">
            <button
              type="button"
              onClick={goToRegistro}
              style={{
                background: "none",
                border: "none",
                color: "#007bff",
                textDecoration: "underline",
                cursor: "pointer",
              }}
            >
              Registrarse
            </button>
          </div>
        </div>
      </form>
    </div>
  );
};

export default Login;
