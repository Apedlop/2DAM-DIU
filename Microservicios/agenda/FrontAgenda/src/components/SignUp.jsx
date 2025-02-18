import React, { useState } from "react";
import { signUpWithEmail } from "../firebase.js"; // Asegúrate de importar las funciones de firebase

const SignUp = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (password !== confirmPassword) {
      setError("Las contraseñas no coinciden");
      return;
    }

    setError("");
    setLoading(true);

    try {
      await signUpWithEmail(email, password, {
        displayName: email.split("@")[0],
      }); // Se puede personalizar el nombre
      alert("Registro exitoso. Verifica tu correo para continuar.");
    } catch (err) {
      setError(err.message);
      console.error("Error al registrarse", err);
    }

    setLoading(false);
  };

  return (
    <div className="auth-form">
      <h2>Registro</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="email">Correo Electrónico:</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div>
          <label htmlFor="password">Contraseña:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <div>
          <label htmlFor="confirmPassword">Confirmar Contraseña:</label>
          <input
            type="password"
            id="confirmPassword"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            required
          />
        </div>
        {error && <p className="error">{error}</p>}
        <button type="submit" disabled={loading}>
          Registrar
        </button>
      </form>
    </div>
  );
};

export default SignUp;
