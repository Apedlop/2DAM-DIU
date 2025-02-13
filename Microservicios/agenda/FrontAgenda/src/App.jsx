import { useState } from "react";
import reactLogo from "./assets/react.svg";
import viteLogo from "/vite.svg";
import React, { Component } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route, Routes, Link } from "react-router-dom";
import "./App.css";
import AgendaList from "./components/AgendaList";
// import AgendaAdd from "./components/AgendaAdd";

function App() {
  return (
    <div>
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
              <Link to={"/add"} className="nav-link">
                Añadir
              </Link>
            </li>
          </div>
        </nav>
        <Routes>
          <Route path="/" element={<AgendaList />} />
          <Route path="/agenda" element={<AgendaList />} />
          {/* <Route path="/add" element={<AgendaAdd />} /> */}
        </Routes>
      </Router>
    </div>
  );
}

export default App;
