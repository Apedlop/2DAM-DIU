import React, { useState } from "react";
import { signUpWithEmail } from "../firebase"; // Asegúrate de importar la función correctamente

const SignUp = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Verificar que las contraseñas coinciden
    if (password !== confirmPassword) {
      setError("Las contraseñas no coinciden");
      return;
    }

    // Limpiar error previo y comenzar el proceso de carga
    setError("");
    setLoading(true);

    try {
      // Llamar a la función de Firebase para registrar al usuario
      await signUpWithEmail(email, password, {
        displayName: email.split("@")[0], // Usar la parte del email como nombre por defecto
      });
      alert("Registro exitoso. Verifica tu correo para continuar.");
      // Aquí puedes redirigir al usuario a otra página si lo deseas
    } catch (err) {
      setError(err.message); // Mostrar el error si ocurre algo
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
          {loading ? "Cargando..." : "Registrar"}
        </button>
      </form>
    </div>
  );
};

export default SignUp;
