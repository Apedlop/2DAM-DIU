import React, { useState, useEffect } from "react";
import tutorialsService from "../service/tutorials.service";

function TutorialsDetails({ tutorials }) {
  const [selectedTutorial, setSelectedTutorial] = useState(null);

  const getTutorial = (tutorialId) => {
    tutorialsService
      .get(tutorialId)
      .then((response) => {
        setSelectedTutorial(response.data);
        console.log(response.data);
      })
      .catch((e) => {
        console.log(e);
      });
  };

  return (
    <div>
      <h5>Lista de Tutoriales:</h5>
      <ul>
        {tutorials && tutorials.length > 0 ? (
          tutorials.map((tutorial) => (
            <li key={tutorial.id} onClick={() => getTutorial(tutorial.id)}>
              <strong>{tutorial.title}</strong>
              <p>{tutorial.description}</p>
            </li>
          ))
        ) : (
          <p>No hay tutoriales disponibles para esta persona.</p>
        )}
      </ul>

      {selectedTutorial && (
        <div>
          <h6>Detalles del Tutorial:</h6>
          <p>
            <strong>Título:</strong> {selectedTutorial.title}
          </p>
          <p>
            <strong>Descripción:</strong> {selectedTutorial.description}
          </p>
          <p>
            <strong>Publicado:</strong>{" "}
            {selectedTutorial.published ? "Sí" : "No"}
          </p>
        </div>
      )}
    </div>
  );
}

export default TutorialsDetails;
