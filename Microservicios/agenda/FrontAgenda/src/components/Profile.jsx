import React, { useContext } from "react";
import { UserContext } from "../provider/UserProvider";
import { Link } from "react-router-dom";
import "./style/Profile.css"; // Añade estilos adicionales si es necesario

function Profile() {
  const { user } = useContext(UserContext); // Acceder al contexto del usuario

  if (!user) {
    return (
      <div className="alert alert-warning" role="alert">
        No estás autenticado. Inicia sesión para ver tu perfil.
      </div>
    );
  }

  return (
    <div className="profile-container">
      <div className="profile-info">
        <h1 className="profile-title">Mi Perfil</h1>
        <div className="profile-details">
          <img src={user.photoURL} alt="User" className="profile-image" />
          <p>
            <b>Nombre:</b> {user.displayName}
          </p>
          <p>
            <b>Correo electrónico:</b> {user.email}
          </p>
        </div>
      </div>
    </div>
  );
}

export default Profile;
