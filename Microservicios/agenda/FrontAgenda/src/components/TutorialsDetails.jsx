import React, { useEffect, useState } from "react";
import tutorialsService from "../service/tutorials.service";

function TutorialsDetails({ tutorials }) {
  const [selectedTutorial, setSelectedTutorial] = useState(null);

  useEffect(() => {
    if (tutorials) { // Asegurarse de que `tutorials` es un ID válido
      tutorialsService.get(tutorials) // `tutorials` ahora es el ID
        .then((response) => {
          setSelectedTutorial(response.data); // Guardar los datos del tutorial
          console.log(response.data); // Opcional: para ver los datos en la consola
        })
        .catch((e) => {
          console.log(e); // Manejo de errores
        });
    }
  }, [tutorials]); // Este `useEffect` se ejecutará cuando cambie el valor de `tutorials`

  return (
    <div>
      <h5>Detalles del Tutorial:</h5>

      {selectedTutorial ? (
        <div>
          <h6>{selectedTutorial.title}</h6>
          <p>{selectedTutorial.description}</p>
          <img src={selectedTutorial.imagen} alt="Imagen del tutorial" />
          <p><strong>Publicado:</strong> {selectedTutorial.published ? "Sí" : "No"}</p>
        </div>
      ) : (
        <p>Cargando detalles del tutorial...</p>
      )}
    </div>
  );
}

export default TutorialsDetails;
