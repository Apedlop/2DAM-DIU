import React, { useEffect, useState } from "react";
import tutorialsService from "../service/tutorials.service"; // Ajusta la ruta según tu proyecto

const Tutoriales = ({ tutorials }) => {
  const [datosTutoriales, setDatosTutoriales] = useState([]);

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

  return (
    <div>
      <h2>Lista de Tutoriales</h2>
      <ul>
        {datosTutoriales.map((tutorial, index) => (
          <li key={index}>
            <h3>{tutorial.title}</h3>
            <p>{tutorial.description}</p>
            <p>{tutorial.published ? "Publicado" : "No Publicado"}</p>
            <img src={tutorial.imagen} alt={tutorial.title} />
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Tutoriales;
