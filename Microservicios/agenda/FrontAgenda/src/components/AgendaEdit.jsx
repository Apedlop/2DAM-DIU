import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { useNavigate } from "react-router-dom";
import agendaService from "../service/agenda.service";
import tutorialsService from "../service/tutorials.service"; 
import { useParams } from "react-router-dom"; 

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
  
  const navegar = useNavigate(); // Hook para redireccionar

  useEffect(() => {
    // Obtener la lista de tutoriales publicados
    tutorialsService
      .get(id)
      .then((response) => {
        setPersona(response.data); // Asumiendo que la respuesta contiene la lista de tutoriales
      })
      .catch((error) => {
        console.log("Error fetching tutorials:", error);
      });
  }, []);

  const valoresEditados = (e) => {
    const { id, value } = e.target;
    setPersona({
      ...persona,
      [id]: value,
    });
  };

  const updateAgenda = () => {
    agendaService.update(id, persona)
    .then(() => {
      navegar("/agenda")
    })
    .catch((e) => {
      console.log(e)
    })
  };

  const handleChange = (e) => {
    const { id, value } = e.target;
    setpersona((prevState) => ({
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
            value={persona.nombre}
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
            value={persona.apellidos}
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
            value={persona.calle}
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
            value={persona.codigoPostal}
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
            value={persona.ciudad}
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
            value={persona.cumpleanos}
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
