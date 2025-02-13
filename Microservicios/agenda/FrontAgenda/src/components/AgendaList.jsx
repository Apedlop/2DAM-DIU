import React, { useEffect, useState } from "react";
import agendaService from "../service/agenda.service";
import { Link } from "react-router-dom";
import "./AgendaList.css"
import "bootstrap/dist/css/bootstrap.min.css";

function AgendaList() {
  const [personas, setPersonas] = useState([]); // Lista de personas
  const [selectPersona, setSelectPersona] = useState(null); // Persona seleccionada
  const [idSelect, setIdSelect] = useState(-1);

  // Cuando se monta el compoenente, se carga la Agenda
  useEffect(() => {
    getAllAgenda();
  }, []);

  const getAllAgenda = () => {
    agendaService
      .getAll()
      .then((response) => {
        setPersonas(response.data);
        console.log(response.data);
      })
      .catch((e) => {
        console.log(e);
      });
  };

  const setActivateAgenda = (persona, index) => {
    setSelectPersona(persona);
    setIdSelect(index);
  };

  return (
    <div className="list row">
      <div className="col-md-4">
        <h4>Lista Agenda</h4>
        <ul className="list-group">
          {personas &&
            personas.map((persona, index) => (
              <li
                className={
                  "list-group-item" + (index === selectPersona ? "active" : "")
                }
                onClick={() => setActivateAgenda(persona, index)}
                key={index}
              >
                {persona.nombre} {persona.apellidos}
              </li>
            ))}
        </ul>
        <table className="table">
            <thead>
              <tr>
                <td>Nombre</td>
                <td>Apellidos</td>
              </tr>
            </thead>
            <tbody>
              {personas.length > 0 ? (
                [...personas].reverse().map((persona, index) => (
                  <tr key={index}>
                    <td>{persona.nombre}</td>
                    <td>{persona.apellidos}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="3">No hay conactos...</td>
                </tr>
              )}
            </tbody>
        </table>
      </div>
      <div className="col-md-8">
        {selectPersona ? (
          <div>
            <h4>Persona</h4>
            <div>
              <label>
                <strong>Nombre: </strong>
              </label>
              {" " + selectPersona.nombre}
            </div>
            <Link
              to={"/agenda/" + selectPersona.id}
              className="badge negro">
              Editar
            </Link>
          </div>
        ) : (
          <div>
            <p>Please click on a Tutorial...</p>
          </div>
        )}
      </div>
    </div>
  );
}

export default AgendaList;
