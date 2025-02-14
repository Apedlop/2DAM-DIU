import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { useNavigate } from "react-router-dom";
import agendaService from "../service/agenda.service";
import tutorialsService from "../service/tutorials.service"; // Asegúrate de tener un servicio para obtener tutoriales
import TutorialsAdd from "./TutorialsAdd";

function AgendaEdit() {

  const { id } = useParams(); // Obtener el id de la agenda a editar

  const [persona, setPersona] = useState({
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
  const [tutorialDetail, setTutorialDetail] = useState(null); // Detalle de un tutorial
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
    agendaService
      .create({ ...newPersona, tutorials: selectedTutorials })
      .then(() => {
        navegar.push("/agenda");
      })
      .catch((e) => {
        console.log(e);
      });
  };

  const agregarTutorial = (tutorialId) => {
    // Agregar tutorial seleccionado
    setSelectedTutorials((prevSelected) => [...prevSelected, tutorialId]);
  };

  const handleChange = (e) => {
    const { id, value } = e.target;
    setNewPersona((prevState) => ({
      ...prevState,
      [id]: value,
    }));
  };

  const mostrarDetalleTutorial = (tutorialId) => {
    const tutorial = tutorials.find((t) => t.id === tutorialId);
    setTutorialDetail(tutorial);
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

        {/* Lista de tutoriales */}
        <div className="form-group">
          <label htmlFor="tutoriales">Selecciona Tutoriales</label>
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

        <button
          type="button"
          className="btn btn-primary"
          onClick={createAgenda}
        >
          Crear Agenda
        </button>
      </form>

      {/* Detalle de tutorial */}
      {tutorialDetail && (
        <div className="tutorial-detail mt-5">
          <h3>{tutorialDetail.title}</h3>
          <img
            src={tutorialDetail.coverImageUrl}
            alt={tutorialDetail.title}
            style={{ width: "100%", height: "auto" }}
          />
          <p>{tutorialDetail.description}</p>
        </div>
      )}
    </div>
  );
}

export default AgendaEdit;
