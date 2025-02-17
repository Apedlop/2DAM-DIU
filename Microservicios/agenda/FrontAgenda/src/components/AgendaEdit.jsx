import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { useNavigate } from "react-router-dom";
import agendaService from "../service/agenda.service";
import tutorialsService from "../service/tutorials.service";
import { useParams } from "react-router-dom";

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

  const elegirTutorial = (tutorialId) => {
    // Comprobar si el tutorial ya está seleccionado
    const estaSeleccionado = selectPersona.tutorials.includes(tutorialId);

    if (estaSeleccionado) {
      // Si está seleccionado, se quita de la lista
      setSelectPersona({
        ...selectPersona,
        tutorials: selectPersona.tutorials.filter((id) => id !== tutorialId),
      });
    } else {
      // Si no está seleccionado, se agrega
      setSelectPersona({
        ...selectPersona,
        tutorials: [...selectPersona.tutorials, tutorialId],
      });
    }
  };

  return (
    <div className="container mt-4">
      <h2>Editar Persona</h2>
      <form>
        <div className="mb-3">
          <label htmlFor="nombre" className="form-label">
            Nombre:
          </label>
          <input
            type="text"
            className="form-control"
            id="nombre"
            value={selectPersona.nombre}
            onChange={valoresEditados}
          />
        </div>

        <div className="mb-3">
          <label htmlFor="apellidos" className="form-label">
            Apellidos:
          </label>
          <input
            type="text"
            className="form-control"
            id="apellidos"
            value={selectPersona.apellidos}
            onChange={valoresEditados}
          />
        </div>

        <div className="mb-3">
          <label htmlFor="calle" className="form-label">
            Calle:
          </label>
          <input
            type="text"
            className="form-control"
            id="calle"
            value={selectPersona.calle}
            onChange={valoresEditados}
          />
        </div>

        <div className="mb-3">
          <label htmlFor="codigoPostal" className="form-label">
            Código Postal:
          </label>
          <input
            type="text"
            className="form-control"
            id="codigoPostal"
            value={selectPersona.codigoPostal}
            onChange={valoresEditados}
          />
        </div>

        <div className="mb-3">
          <label htmlFor="ciudad" className="form-label">
            Ciudad:
          </label>
          <input
            type="text"
            className="form-control"
            id="ciudad"
            value={selectPersona.ciudad}
            onChange={valoresEditados}
          />
        </div>

        <div className="mb-3">
          <label htmlFor="cumpleanos" className="form-label">
            Cumpleaños:
          </label>
          <input
            type="date"
            className="form-control"
            id="cumpleanos"
            value={selectPersona.cumpleanos}
            onChange={valoresEditados}
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Tutoriales:</label>
          <div>
            {tutorials && tutorials.length > 0 ? (
              tutorials.map((tutorial) => (
                <div className="form-check" key={tutorial.id}>
                  <input
                    className="form-check-input"
                    type="checkbox"
                    value={tutorial.id}
                    checked={selectPersona.tutorials.includes(tutorial.id)}
                    onChange={() => elegirTutorial(tutorial.id)}
                    id={`tutorial-${tutorial.id}`}
                  />
                  <label
                    className="form-check-label"
                    htmlFor={`tutorial-${tutorial.id}`}
                  >
                    {tutorial.title}
                  </label>
                </div>
              ))
            ) : (
              <p>No hay tutoriales disponibles</p>
            )}
          </div>
        </div>

        <button type="button" className="btn btn-primary" onClick={editAgenda}>
          Guardar Cambios
        </button>
      </form>
    </div>
  );
}

export default AgendaEdit;
