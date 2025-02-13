import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { useNavigate } from "react-router-dom";
import agendaService from "../service/agenda.service";
import TutorialsAdd from "./TutorialsAdd";

function AgendaAdd() {
  const [newPersona, setNewPersona] = useState({
    nombre: "",
    apellidos: "",
    calle: "",
    codigoPostal: "",
    ciudad: "",
    cumpleanos: "",
    tutorials: []
  });

  const navegar = useNavigate(); // Hook para redireccionar

  const valoresEditados = (e) => {
    const { id, value } = e.target;
    setNewPersona({
      ...newPersona,
      [id]: value,
    });
  };

  const createAgenda = () => {
    agendaService
      .create(newPersona)
      .then(() => {
        navegar.push("/agenda");
      })
      .catch((e) => {
        console.log(e);
      });
  };

  const agregarTutorial = (newTutorial) => {
    setNewPersona(prevState => ({
      ...prevState,
      tutorials: [...prevState.tutorials, newTutorial]
    }));
  };

  const handleChange = (e) => {
    const { id, value } = e.target;
    setNewPersona(prevState => ({
      ...prevState,
      [id]: value
    }));
  };

  return (
    <div>
      <form>
        <div className="form-group">
          <label htmlFor="nombre">Nombre</label>
          <input
            type="text"
            className="form-control"
            id="nombre"
            required
            value={newPersona.nombre}
            onChange={valoresEditados}
          />
        </div>
        <div className="form-group">
          <label htmlFor="apellidos">Apellidos</label>
          <input
            type="text"
            className="form-control"
            id="apellidos"
            required
            value={newPersona.apellidos}
            onChange={valoresEditados}
          />
        </div>
        <div className="form-group">
          <label htmlFor="calle">Calle</label>
          <input
            type="text"
            className="form-control"
            id="calle"
            required
            value={newPersona.calle}
            onChange={valoresEditados}
          />
        </div>
        <div className="form-group">
          <label htmlFor="codigoPostal">Código Postal</label>
          <input
            type="number"
            className="form-control"
            id="codigoPostal"
            required
            value={newPersona.codigoPostal}
            onChange={valoresEditados}
          />
        </div>
        <div className="form-group">
          <label htmlFor="ciudad">Ciudad</label>
          <input
            type="text"
            className="form-control"
            id="ciudad"
            required
            value={newPersona.ciudad}
            onChange={valoresEditados}
          />
        </div>
        <div className="form-group">
          <label htmlFor="cumpleanos">Cumpleaños</label>
          <input
            type="date"
            className="form-control"
            id="cumpleanos"
            required
            value={newPersona.cumpleanos}
            onChange={valoresEditados}
          />
        </div>
        <div>
            <TutorialsAdd agregarTutorial={agregarTutorial}/>
        </div>
      </form>
    </div>
  );
}

export default AgendaAdd;
