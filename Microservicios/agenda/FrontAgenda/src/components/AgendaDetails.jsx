import React, { useEffect, useState } from "react"; 
import { useParams } from "react-router-dom"; 
import agendaService from "../service/agenda.service"; 
import TutorialsDetails from "./TutorialsDetails";

function AgendaDetails() {
  const { id } = useParams(); // Obtener el id de la URL
  const [persona, setPersona] = useState(null); // Inicializar persona como null

  // Usar useEffect para cargar la persona cuando el id cambia
  useEffect(() => {
    agendaService
      .get(id)
      .then((response) => {
        setPersona(response.data); // Establece los datos de la persona
      })
      .catch((e) => {
        console.log(e); // Maneja errores en la carga de datos
      });
  }, [id]); // Se ejecuta cuando cambia el id

  return (
    <div>
      {persona ? (
        <div>
          <h4>Detalles de la agenda</h4>
          <div>
            <label>
              <strong>Nombre:</strong>
            </label>{" "}
            {persona.nombre}
          </div>
          <div>
            <label>
              <strong>Apellidos:</strong>
            </label>{" "}
            {persona.apellidos}
          </div>
          <div>
            <label>
              <strong>Calle:</strong>
            </label>{" "}
            {persona.calle}
          </div>
          <div>
            <label>
              <strong>Código Postal:</strong>
            </label>{" "}
            {persona.codigoPostal}
          </div>
          <div>
            <label>
              <strong>Ciudad:</strong>
            </label>{" "}
            {persona.ciudad}
          </div>
          <div>
            <label>
              <strong>Cumpleaños:</strong>
            </label>{" "}
            {persona.cumpleanos}
          </div>
          <div>
            <label>
              <strong>Tutoriales:</strong>
            </label>{" "}
            {/* Verificamos si persona tiene tutoriales antes de pasarlos */}
            {persona.tutorials && persona.tutorials.length > 0 ? (
              <TutorialsDetails tutorials={persona.tutorials} />
            ) : (
              <p>No hay tutoriales disponibles.</p>
            )}
          </div>
        </div>
      ) : (
        <div>
          <br />
          <p>Selecciona una persona...</p>
        </div>
      )}
    </div>
  );
}

export default AgendaDetails;
