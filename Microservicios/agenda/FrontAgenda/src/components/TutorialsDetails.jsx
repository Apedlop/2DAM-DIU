import React, { useEffect, useState } from "react";
import tutorialsService from "../service/tutorials.service"; // Ajusta la ruta según tu proyecto
import "./style/TutorialsDetails.css"; // Importa los estilos CSS

const TutorialsDetails = ({ tutorials }) => {
  const [datosTutoriales, setDatosTutoriales] = useState([]);
  const [selectedTutorial, setSelectedTutorial] = useState(null); // Tutorial seleccionado

  useEffect(() => {
    const obtenerTutoriales = async () => {
      try {
        const respuestas = await Promise.all(
          tutorials.map((id) => tutorialsService.get(id))
        );

        // Extraer solo la propiedad 'data' de cada respuesta
        const datos = respuestas.map((respuesta) => respuesta.data);
        setDatosTutoriales(datos);
      } catch (error) {
        console.error("Error al obtener los tutoriales:", error);
      }
    };

    obtenerTutoriales();
  }, [tutorials]);

  // Función para manejar la selección de un tutorial
  const handleTutorialClick = (tutorial) => {
    setSelectedTutorial(selectedTutorial === tutorial ? null : tutorial);
  };

  return (
    <div className="tutoriales-container">
      <h2 className="tutoriales-title">Tutoriales Asociados</h2>
      <ul className="tutoriales-list">
        {datosTutoriales.map((tutorial, index) => (
          <li
            key={index}
            className={`tutorial-item ${
              selectedTutorial === tutorial ? "active" : ""
            }`}
            onClick={() => handleTutorialClick(tutorial)}
          >
            <div className="tutorial-summary">
              <h3>{tutorial.title}</h3>
              <p>{tutorial.published ? "Publicado" : "No Publicado"}</p>
            </div>
            {selectedTutorial === tutorial && (
              <div className="tutorial-details">
                <p>{tutorial.description}</p>
                {tutorial.imagen && (
                  <img
                    src={tutorial.imagen}
                    alt={tutorial.title}
                    className="tutorial-image"
                  />
                )}
              </div>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default TutorialsDetails;