import React, { useEffect, useState } from "react";
import agendaService from "../service/agenda.service";
import { Link } from "react-router-dom";
import { FaPlus } from "react-icons/fa";
import "./style/AgendaList.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { ProgressBar, Modal, Button } from "react-bootstrap";

function AgendaList() {
  const [personas, setPersonas] = useState([]); // Lista de personas
  const [totalPersonas, setTotalPersonas] = useState(0); // Cantidad total de personas
  const [selectPersona, setSelectPersona] = useState(null); // Persona seleccionada
  const [idSelect, setIdSelect] = useState(-1);
  const [buscarNombre, setBuscarNombre] = useState("");
  const MAX_PERSONAS = 5;

  // Cuando se monta el componente, se carga la Agenda
  useEffect(() => {
    getAllAgenda();
  }, []);

  const getAllAgenda = () => {
    agendaService
      .getAll()
      .then((response) => {
        setPersonas(response.data);
        setTotalPersonas(response.data.length); // Guardar la cantidad total de personas
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

  const refreshList = () => {
    getAllAgenda();
    setSelectPersona(null);
    setIdSelect(-1);
  };

  const deletePersona = (id) => {
    agendaService
      .delete(id)
      .then(() => {
        refreshList();
      })
      .catch((e) => {
        console.log(e);
      });
  };

  const searchNombre = () => {
    if (buscarNombre === "") {
      // Si no hay texto en la búsqueda, obtener todas las personas
      getAllAgenda();
    } else {
      // Si hay texto, buscar por nombre
      agendaService
        .findByName(buscarNombre)
        .then((response) => {
          setPersonas(response.data);
        })
        .catch((e) => {
          console.log(e);
        });
    }
  };

  const onChangeNombre = (e) => {
    const buscarNombre = e.target.value;
    setBuscarNombre(buscarNombre);
  };

  const obtenerPorcentaje = () => {
    return (totalPersonas / MAX_PERSONAS) * 100; // Usar totalPersonas en lugar de personas.length
  };

  return (
    <div className="row">
      <div className="col-md-8 busqueda">
        <div className="input-group mb-3">
          <input
            type="text"
            className="form-control"
            placeholder="Buscar por nombre"
            value={buscarNombre}
            onChange={onChangeNombre}
          />
          <div className="input-group-append">
            <button
              className="btn btn-secondary"
              type="button"
              onClick={searchNombre}
            >
              <i className="fas fa-search"></i> Buscar
            </button>
          </div>
        </div>
      </div>
      <h1 className="titulo">Lista de Contactos</h1>
      <div className="margenBarraProgreso">
        <ProgressBar
          now={obtenerPorcentaje()}
          label={`${Math.round(obtenerPorcentaje())}%`}
          animated
          className="barraProgreso"
        />
        {totalPersonas >= MAX_PERSONAS && ( // Usar totalPersonas en lugar de personas.length
          <small className="text-muted d-block text-center mt-2">
            Has alcanzado el límite de contactos.
          </small>
        )}
      </div>
      <div className="row">
        {personas.length > 0 ? (
          [...personas].reverse().map((persona, index) => (
            <div
              className="col-md-4 mb-3"
              key={index}
              onClick={() => setActivateAgenda(persona, index)}
            >
              <div
                className={`card ${
                  index === idSelect ? "border-primary" : ""
                }`}
                style={{ cursor: "pointer" }}
              >
                <Link
                  to={"/agenda/" + persona.id}
                  style={{ textDecoration: "none", color: "inherit" }}
                >
                  <div className="card-body">
                    <h4 className="card-title">{persona.nombre}</h4>
                    <p className="card-text">
                      <b>Apellidos:</b> {persona.apellidos} <br />
                      <b>Fecha de nacimiento:</b> {persona.cumpleanos}
                    </p>
                  </div>
                </Link>
                <div className="botones">
                  <Link to={"/agenda/edit/" + persona.id}>
                    <button className="btn btn-warning">Editar</button>
                  </Link>
                  <button
                    className="btn btn-danger"
                    onClick={() => deletePersona(persona.id)}
                  >
                    Eliminar
                  </button>
                </div>
              </div>
            </div>
          ))
        ) : (
          <div className="col-12">
            <div className="alert alert-warning" role="alert">
              No hay contactos...
            </div>
          </div>
        )}
      </div>
      <Link to={"/add"}>
        <button
          className="btn btn-primary boton-flotante"
          title="Añadir nuevo contacto"
          disabled={totalPersonas >= MAX_PERSONAS} // Usar totalPersonas en lugar de personas.length
        >
          <FaPlus />
        </button>
      </Link>
    </div>
  );
}

export default AgendaList;