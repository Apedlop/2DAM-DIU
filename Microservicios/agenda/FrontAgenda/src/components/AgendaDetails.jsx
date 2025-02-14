import React, { useEffect, useState } from "react"; // Asegúrate de importar useEffect correctamente
import { useParams } from "react-router-dom";
import agendaService from "../service/agenda.service";
import TutorialsDetails from "./TutorialsDetails";

function AgendaDetails() {
  const { id } = useParams(); // Aquí debe ser un llamado a la función useParams()
  const [persona, setPersona] = useState(null); // Inicializar persona como null

  // Usar useEffect para cargar la persona cuando el id cambia
  useEffect(() => {
    agendaService
      .get(id)
      .then((response) => {
        setPersona(response.data);
      })
      .catch((e) => {
        console.log(e);
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
            {persona.nombre} {/* Aquí mostramos el nombre */}
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
            {/* Pasamos los tutoriales de la persona al componente TutorialsDetails */}
            <TutorialsDetails tutorials={persona.tutorials} />
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
