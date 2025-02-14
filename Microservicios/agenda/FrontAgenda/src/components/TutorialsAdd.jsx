import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { useNavigate } from "react-router-dom";
import agendaService from "../service/agenda.service";
import tutorialsService from "../service/tutorials.service";

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

  const [tutorialsList, setTutorialsList] = useState([]);
  const navegar = useNavigate(); // Hook para redireccionar

  useEffect(() => {
    // Cargar todos los tutoriales publicados
    tutorialsService
      .getAllPublishedTutorials()
      .then((response) => {
        setTutorialsList(response.data); // Suponiendo que la respuesta tiene la lista de tutoriales
      })
      .catch((e) => {
        console.log(e);
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
      .create(newPersona)
      .then(() => {
        navegar.push("/agenda");
      })
      .catch((e) => {
        console.log(e);
      });
  };

  const handleTutorialSelection = (e) => {
    const { value, checked } = e.target;
    let updatedTutorials = [...newPersona.tutorials];

    if (checked) {
      updatedTutorials.push(value);
    } else {
      updatedTutorials = updatedTutorials.filter(
        (tutorial) => tutorial !== value
      );
    }

    setNewPersona({
      ...newPersona,
      tutorials: updatedTutorials,
    });
  };

  return (
    <div>
      <form>
        {/* ... los otros campos del formulario ... */}

        <div className="form-group">
          <label>Tutoriales publicados:</label>
          <div>
            {tutorialsList.map((tutorial) => (
              <div key={tutorial.id}>
                <input
                  type="checkbox"
                  id={`tutorial-${tutorial.id}`}
                  value={tutorial.id}
                  onChange={handleTutorialSelection}
                />
                <label htmlFor={`tutorial-${tutorial.id}`}>
                  {tutorial.title}
                </label>
              </div>
            ))}
          </div>
        </div>

        <button onClick={createAgenda} className="btn btn-success">
          Añadir
        </button>
      </form>
    </div>
  );
}

export default AgendaAdd;
