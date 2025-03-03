import React, { useState } from "react";
import { getAuth, signInWithEmailAndPassword } from "firebase/auth";
import { useNavigate } from "react-router-dom";
import { Card, Container, Button, Form } from "react-bootstrap";
import "./style/SignIn.css";

const SignIn = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const auth = getAuth();
  const navigate = useNavigate();

  // Esta es la función que se ejecuta al intentar iniciar sesión
  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      await signInWithEmailAndPassword(auth, email, password);
      navigate("/"); // Redirige al usuario a la página principal después del login
    } catch (error) {
      setError(error.message); // Muestra el error si algo sale mal
    }
  };

  // Esta es la función que maneja los cambios de los campos de input
  const onChangeHandler = (e) => {
    const { name, value } = e.target;
    if (name === "userEmail") {
      setEmail(value);
    } else if (name === "userPassword") {
      setPassword(value);
    }
  };

  return (
    <div className="sign-in-container">
      <h1 className="sign-in-title">Iniciar sesión</h1>
      <div className="form-container">
        {error && <div className="error-message">{error}</div>}
        <form onSubmit={handleLogin}>
          <label htmlFor="userEmail" className="block">
            Correo:
          </label>
          <input
            type="email"
            className="input-field"
            name="userEmail"
            value={email}
            placeholder="Ej: prueba@prueba.com"
            id="userEmail"
            onChange={onChangeHandler}
          />
          <label htmlFor="userPassword" className="block">
            Contraseña:
          </label>
          <input
            type="password"
            className="input-field"
            name="userPassword"
            value={password}
            placeholder="Tu contraseña"
            id="userPassword"
            onChange={onChangeHandler}
          />
          <button className="sign-in-button" type="submit">
            Iniciar sesión
          </button>
        </form>
      </div>
    </div>
  );
};

export default SignIn;
