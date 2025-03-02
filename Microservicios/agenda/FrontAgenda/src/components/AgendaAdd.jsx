import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { useNavigate } from "react-router-dom";
import agendaService from "../service/agenda.service";
import tutorialsService from "../service/tutorials.service";
import "./style/AgendaAdd.css"; // Importa los estilos CSS

function AgendaAdd() {
  const [newPersona, setNewPersona] = useState({
    nombre: "",
    apellidos: "",
    calle: "",
    codigoPostal: "",
    ciudad: "",
    cumpleanos: "",
    tutorials: [],
  });
  const [tutorials, setTutorials] = useState([]); // Para almacenar los tutoriales publicados
  const [selectedTutorials, setSelectedTutorials] = useState([]); // Tutoriales seleccionados
  const [error, setError] = useState(""); // Para manejar errores de validación
  const navegar = useNavigate(); // Hook para redireccionar

  useEffect(() => {
    // Obtener la lista de tutoriales publicados
    tutorialsService
      .getAllPublishedTutorials()
      .then((response) => {
        setTutorials(response.data); // Asumiendo que la respuesta contiene la lista de tutoriales
      })
      .catch((error) => {
        console.log("Error fetching tutorials:", error);
      });
  }, []);

  const valoresEditados = (e) => {
    const { id, value } = e.target;
    setNewPersona({
      ...newPersona,
      [id]: value,
    });
  };

  const createAgenda = () => {
    // Validación personalizada para campos vacíos
    if (
      !newPersona.nombre ||
      !newPersona.apellidos ||
      !newPersona.calle ||
      !newPersona.codigoPostal ||
      !newPersona.ciudad ||
      !newPersona.cumpleanos 
    ) {
      setError("Todos los campos son obligatorios.");
      return;
    }

    // Si todo está bien, se crea la agenda
    agendaService
      .create({ ...newPersona, tutorials: selectedTutorials })
      .then(() => {
        navegar("/agenda");
      })
      .catch((e) => {
        console.log(e);
      });
  };

  const cancelar = () => {
    navegar("/agenda");
  };

  return (
    <div className="agenda-add-container">
      <h2 className="agenda-add-title">Añadir Nueva Persona</h2>
      {error && <div className="alert alert-danger">{error}</div>} {/* Mostrar mensaje de error */}
      <form>
        <div className="row rowAdd">
          {/* Columna izquierda */}
          <div className="col-md-6">
            <div className="form-group">
              <label htmlFor="nombre" className="form-label">
                Nombre
              </label>
              <input
                type="text"
                className="form-control"
                id="nombre"
                value={newPersona.nombre}
                onChange={valoresEditados}
                placeholder="Ingresa el nombre"
              />
            </div>
            <div className="form-group">
              <label htmlFor="calle" className="form-label">
                Calle
              </label>
              <input
                type="text"
                className="form-control"
                id="calle"
                value={newPersona.calle}
                onChange={valoresEditados}
                placeholder="Ingresa la calle"
              />
            </div>
            <div className="form-group">
              <label htmlFor="ciudad" className="form-label">
                Ciudad
              </label>
              <input
                type="text"
                className="form-control"
                id="ciudad"
                value={newPersona.ciudad}
                onChange={valoresEditados}
                placeholder="Ingresa la ciudad"
              />
            </div>
          </div>

          {/* Columna derecha */}
          <div className="col-md-6">
            <div className="form-group">
              <label htmlFor="apellidos" className="form-label">
                Apellidos
              </label>
              <input
                type="text"
                className="form-control"
                id="apellidos"
                value={newPersona.apellidos}
                onChange={valoresEditados}
                placeholder="Ingresa los apellidos"
              />
            </div>
            <div className="form-group">
              <label htmlFor="codigoPostal" className="form-label">
                Código Postal
              </label>
              <input
                type="number"
                className="form-control"
                id="codigoPostal"
                value={newPersona.codigoPostal}
                onChange={valoresEditados}
                placeholder="Ingresa el código postal"
              />
            </div>
            <div className="form-group">
              <label htmlFor="cumpleanos" className="form-label">
                Cumpleaños
              </label>
              <input
                type="date"
                className="form-control"
                id="cumpleanos"
                value={newPersona.cumpleanos}
                onChange={valoresEditados}
              />
            </div>
          </div>
        </div>

        {/* Lista de tutoriales (ocupa todo el ancho) */}
        <div className="form-group">
          <label htmlFor="tutoriales" className="form-label">
            Selecciona Tutoriales
          </label>
          <select
            id="tutoriales"
            multiple
            className="form-control"
            onChange={(e) => {
              const selected = Array.from(
                e.target.selectedOptions,
                (option) => option.value
              );
              setSelectedTutorials(selected);
            }}
          >
            {tutorials.map((tutorial) => (
              <option key={tutorial.id} value={tutorial.id}>
                {tutorial.title}
              </option>
            ))}
          </select>
        </div>

        {/* Botones (ocupan todo el ancho) */}
        <div className="form-group text-center marginButton">
          <button
            type="button"
            className="btn btn-primary"
            onClick={createAgenda}
          >
            Crear Agenda
          </button>
          <button
            type="button"
            className="btn btn-cancelar"
            onClick={cancelar}
          >
            Cancelar
          </button>
        </div>
      </form>
    </div>
  );
}

export default AgendaAdd;