import { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route, Routes, Link } from "react-router-dom";
import "./App.css";
import AgendaList from "./components/AgendaList";
import AgendaAdd from "./components/AgendaAdd";
import AgendaEdit from "./components/AgendaEdit";
import AgendaDetails from "./components/AgendaDetails";
import TutorialsAdd from "./components/TutorialsAdd";
import TutorialsList from "./components/TutorialsList";
import SignIn from "./components/SignIn";
import UserProvider from "./provider/UserProvider";
import TutorialsEdit from "./components/TutorialsEdit";

function App() {
  return (
    <div>
      <Router>
        {/* Navbar fija en la parte superior */}
        <nav className="navbar navbar-expand navbar-dark fixed-top">
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
              <Link to={"/signIn"} className="nav-link">
                Login
              </Link>
            </li>
          </div>
        </nav>

        {/* Contenido principal con margen superior para evitar solapamiento con la navbar */}
        <div className="main-content">
          <Routes>
            <Route path="/" element={<AgendaList />} />
            <Route path="/agenda" element={<AgendaList />} />
            <Route path="/add" element={<AgendaAdd />} />
            <Route path="/addTutorials" element={<TutorialsAdd />} />
            <Route path="/agenda/edit/:id" element={<AgendaEdit />} />
            <Route path="/agenda/:id" element={<AgendaDetails />} />
            <Route path="/tutorials" element={<TutorialsList />} />
            <Route path="/tutorials/edit/:id" element={<TutorialsEdit />} />
            <Route path="/signIn" element={<SignIn />} />
          </Routes>
        </div>
      </Router>
    </div>
  );
}

export default App;