import React, { useContext } from "react";
import { UserContext } from "../provider/UserProvider";
import { useNavigate } from "react-router-dom";
import { getAuth, signOut } from "firebase/auth";
import { auth } from "../firebase"; // Asegúrate de que esta importación esté correctamente configurada

const ProfilePage = () => {
  const user = useContext(UserContext);
  const { photoURL, displayName, email } = user;
  const navigate = useNavigate(); // Usamos useNavigate para la redirección

  // Función para manejar la salida del usuario
  const handleSignOut = () => {
    signOut(auth)
      .then(() => {
        // Redirige a la página de inicio de sesión después de cerrar sesión
        navigate("/");
      })
      .catch((error) => {
        console.error("Error signing out: ", error);
      });
  };

  return (
    <Router>
      <nav className="navbar navbar-expand navbar-dark bg-dark">
        <Link to={"/agenda"} className="navbar-brand">
          Agenda
        </Link>
        <div className="navbar-nav mr-auto">
          <li className="nav-item">
            <Link to={"/agenda"} className="nav-link">
              Agenda
            </Link>
          </li>
          <li className="nav-item">
            <Link to={"/tutorials"} className="nav-link">
              Tutoriales
            </Link>
          </li>
          <li className="nav-item">
            <div>
                {displayName}
                <img src="{photoURL}" alt="{displayName}" />
            </div>
          </li>
        </div>
      </nav>
      <Routes>
        <Route path="/" element={<AgendaList />} />
        <Route path="/agenda" element={<AgendaList />} />
        <Route path="/add" element={<AgendaAdd />} />
        <Route path="/agenda/edit/:id" element={<AgendaEdit />} />
        <Route path="/agenda/:id" element={<AgendaDetails />} />
        <Route path="/tutorials" element={<TutorialsList />} />
      </Routes>
    </Router>
  );
};

export default ProfilePage;
