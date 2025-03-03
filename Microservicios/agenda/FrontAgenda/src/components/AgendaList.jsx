import React, { useEffect, useState, useContext } from "react";
import agendaService from "../service/agenda.service";
import { Link } from "react-router-dom";
import { FaPlus, FaTrash, FaEdit } from "react-icons/fa";
import "./style/AgendaList.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { ProgressBar } from "react-bootstrap";
import { UserContext } from "../provider/UserProvider";

function AgendaList() {
  const { user } = useContext(UserContext); // Acceder al contexto del usuario
  const [personas, setPersonas] = useState([]); // Lista de personas
  const [totalPersonas, setTotalPersonas] = useState(0); // Cantidad total de personas
  const [buscarNombre, setBuscarNombre] = useState("");
  const MAX_PERSONAS = 100;

  // Cargar la lista de contactos al montar el componente
  useEffect(() => {
    getAllAgenda();
  }, []);

  const getAllAgenda = () => {
    agendaService
      .getAll()
      .then((response) => {
        setPersonas(response.data);
        setTotalPersonas(response.data.length);
      })
      .catch((e) => {
        console.log(e);
      });
  };

  const searchNombre = () => {
    if (buscarNombre === "") {
      getAllAgenda();
    } else {
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
    setBuscarNombre(e.target.value);
  };

  const obtenerPorcentaje = () => {
    return (totalPersonas / MAX_PERSONAS) * 100;
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
        {/* Contenedor para el porcentaje y la barra de progreso */}
        <div className="contenedorBarraProgreso">
          {/* Porcentaje arriba y centrado */}
          <div className="porcentaje">{Math.round(obtenerPorcentaje())}%</div>

          {/* Barra de progreso */}
          <ProgressBar
            now={obtenerPorcentaje()}
            animated
            className="barraProgreso"
          />
        </div>
      </div>

      <div className="row">
        {personas.length > 0 ? (
          [...personas].reverse().map((persona, index) => (
            <div className="col-md-4 mb-3" key={index}>
              <div className="card" style={{ cursor: "pointer" }}>
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

                {user && ( // Solo muestra botones de edición/eliminación si el usuario está logueado
                  <div className="botones">
                    <Link to={"/agenda/edit/" + persona.id}>
                      <button className="btn btn-warning">
                        {" "}
                        <FaEdit /> Editar
                      </button>
                    </Link>
                    <button
                      className="btn btn-danger"
                      onClick={() =>
                        agendaService.delete(persona.id).then(getAllAgenda)
                      }
                    >
                      <FaTrash /> Eliminar
                    </button>
                  </div>
                )}
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

      {user && ( // Solo muestra el botón de añadir si el usuario está logueado
        <Link to={"/add"}>
          <button
            className="btn btn-primary boton-flotante"
            title="Añadir nuevo contacto"
            disabled={totalPersonas >= MAX_PERSONAS}
          >
            <FaPlus />
          </button>
        </Link>
      )}
    </div>
  );
}

export default AgendaList;
