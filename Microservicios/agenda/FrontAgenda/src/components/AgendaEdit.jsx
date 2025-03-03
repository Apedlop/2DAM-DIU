import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { useNavigate, useParams } from "react-router-dom";
import agendaService from "../service/agenda.service";
import tutorialsService from "../service/tutorials.service";
import "./style/AgendaAdd.css"; // Reutilizamos los estilos de AgendaAdd

function AgendaEdit() {
  const { id } = useParams(); // Obtener el ID de la agenda a editar
  const navegar = useNavigate(); // Hook para redireccionar

  const [selectPersona, setSelectPersona] = useState({
    nombre: "",
    apellidos: "",
    calle: "",
    codigoPostal: "",
    ciudad: "",
    cumpleanos: "",
    tutorials: [],
  });

  const [tutorials, setTutorials] = useState([]); // Lista de tutoriales publicados
  const [selectedTutorials, setSelectedTutorials] = useState([]); // Tutoriales seleccionados
  const [error, setError] = useState(""); // Manejo de errores

  useEffect(() => {
    // Obtener la información de la persona
    agendaService
      .get(id)
      .then((response) => {
        const persona = response.data;
        setSelectPersona({
          ...persona,
          tutorials: persona.tutorials || [], // Asegurar que sea un arreglo
        });
        setSelectedTutorials(persona.tutorials?.map((t) => t.id) || []);
      })
      .catch((error) => {
        console.log("Error al obtener los datos:", error);
      });

    // Obtener la lista de tutoriales publicados
    tutorialsService
      .getAllPublishedTutorials()
      .then((response) => {
        setTutorials(response.data);
      })
      .catch((error) => {
        console.log("Error al obtener tutoriales:", error);
      });
  }, [id]);

  const valoresEditados = (e) => {
    const { id, value } = e.target;
    setSelectPersona({
      ...selectPersona,
      [id]: value,
    });
  };

  const editAgenda = () => {
    // Validar que ningún campo esté vacío
    if (
      !selectPersona.nombre ||
      !selectPersona.apellidos ||
      !selectPersona.calle ||
      !selectPersona.codigoPostal ||
      !selectPersona.ciudad ||
      !selectPersona.cumpleanos
    ) {
      setError("Todos los campos son obligatorios.");
      return;
    }

    // Preparar datos para actualización
    const updatePersona = {
      ...selectPersona,
      tutorials: selectedTutorials,
    };

    agendaService
      .update(id, updatePersona)
      .then(() => {
        navegar("/agenda");
      })
      .catch((e) => {
        console.log("Error al actualizar:", e);
      });
  };

  const cancelar = () => {
    navegar("/agenda");
  };

  return (
    <div className="agenda-add-container">
      <h2 className="agenda-add-title">Editar Persona</h2>
      {error && <div className="alert alert-danger">{error}</div>}{" "}
      {/* Mostrar mensaje de error */}
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
                value={selectPersona.nombre}
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
                value={selectPersona.calle}
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
                value={selectPersona.ciudad}
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
                value={selectPersona.apellidos}
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
                value={selectPersona.codigoPostal}
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
                value={selectPersona.cumpleanos}
                onChange={valoresEditados}
              />
            </div>
          </div>
        </div>

        {/* Lista de tutoriales */}
        <div className="form-group">
          <label htmlFor="tutoriales" className="form-label">
            Selecciona Tutoriales
          </label>
          <select
            id="tutoriales"
            multiple
            className="form-control"
            value={selectedTutorials}
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

        {/* Botones */}
        <div className="form-group text-center marginButton">
          <button
            type="button"
            className="btn btn-primary"
            onClick={editAgenda}
          >
            Guardar Cambios
          </button>
          <button type="button" className="btn btn-cancelar" onClick={cancelar}>
            Cancelar
          </button>
        </div>
      </form>
    </div>
  );
}

export default AgendaEdit;
