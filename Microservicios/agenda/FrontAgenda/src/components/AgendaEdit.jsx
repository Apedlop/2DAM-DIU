import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { useNavigate, useParams } from "react-router-dom";
import agendaService from "../service/agenda.service";
import tutorialsService from "../service/tutorials.service";
import "./style/AgendaAdd.css"; // Importa los mismos estilos que AgendaAdd

function AgendaEdit() {
  const { id } = useParams(); // Obtener el id de la agenda a editar

  const [selectPersona, setSelectPersona] = useState({
    nombre: "",
    apellidos: "",
    calle: "",
    codigoPostal: "",
    ciudad: "",
    cumpleanos: "",
    tutorials: [], // Asegurarse de que sea siempre un arreglo
  });

  const [tutorials, setTutorials] = useState([]); // Lista de tutoriales disponibles

  const navegar = useNavigate(); // Hook para redireccionar

  useEffect(() => {
    // Obtener la información de la persona
    agendaService
      .get(id)
      .then((response) => {
        setSelectPersona({
          ...response.data,
          tutorials: response.data.tutorials || [], // Asegurarse de que 'tutorials' sea un arreglo
        });
      })
      .catch((error) => {
        console.log("Error al obtener los datos:", error);
      });

    // Obtener la lista de tutoriales disponibles
    tutorialsService
      .getAll()
      .then((response) => {
        setTutorials(response.data); // Asegurarse de que siempre sea un arreglo
      })
      .catch((error) => {
        console.log("Error al obtener tutoriales:", error);
      });
  }, [id]);

  const editAgenda = () => {
    const updatePersona = {
      id: selectPersona.id,
      nombre: selectPersona.nombre,
      apellidos: selectPersona.apellidos,
      calle: selectPersona.calle,
      codigoPostal: selectPersona.codigoPostal,
      ciudad: selectPersona.ciudad,
      cumpleanos: selectPersona.cumpleanos,
      tutorials: selectPersona.tutorials,
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

  const valoresEditados = (e) => {
    const { id, value } = e.target;
    setSelectPersona({
      ...selectPersona,
      [id]: value,
    });
  };

  const handleTutorialChange = (e) => {
    const selected = Array.from(e.target.selectedOptions, (option) => option.value);
    setSelectPersona({
      ...selectPersona,
      tutorials: selected,
    });
  };

  return (
    <div className="agenda-add-container">
      <h2 className="agenda-add-title">Editar Persona</h2>
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
                required
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
                required
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
                required
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
                required
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
                required
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
                required
                value={selectPersona.cumpleanos}
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
            value={selectPersona.tutorials}
            onChange={handleTutorialChange}
          >
            {tutorials && tutorials.length > 0 ? (
              tutorials.map((tutorial) => (
                <option key={tutorial.id} value={tutorial.id}>
                  {tutorial.title}
                </option>
              ))
            ) : (
              <option disabled>No hay tutoriales disponibles</option>
            )}
          </select>
        </div>

        {/* Botones (ocupan todo el ancho) */}
        <div className="form-group text-center marginButton">
          <button
            type="button"
            className="btn btn-primary"
            onClick={editAgenda}
          >
            Guardar Cambios
          </button>
          <button
            type="button"
            className="btn btn-cancelar"
            onClick={() => navegar("/agenda")}
          >
            Cancelar
          </button>
        </div>
      </form>
    </div>
  );
}

export default AgendaEdit;