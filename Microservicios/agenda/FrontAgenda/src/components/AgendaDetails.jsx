import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import agendaService from "../service/agenda.service";
import TutorialsDetails from "./TutorialsDetails";
import "./style/AgendaDetails.css"; // Importa los estilos CSS

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
    <div className="agenda-details-container">
      {persona ? (
        <div className="agenda-details-card">
          <div className="agenda-header">
            <h2 className="agenda-name">
              {persona.nombre} {persona.apellidos}
            </h2>
          </div>

          <div className="agenda-body">
            <div className="agenda-info">
              <div className="info-item">
                <span className="info-label">Calle:</span>
                <span className="info-value">{persona.calle}</span>
              </div>
              <div className="info-item">
                <span className="info-label">Código Postal:</span>
                <span className="info-value">{persona.codigoPostal}</span>
              </div>
              <div className="info-item">
                <span className="info-label">Ciudad:</span>
                <span className="info-value">{persona.ciudad}</span>
              </div>
              <div className="info-item">
                <span className="info-label">Cumpleaños:</span>
                <span className="info-value">{persona.cumpleanos}</span>
              </div>
            </div>

            {/* Tutoriales */}
            <div className="tutorials-section">
              {persona.tutorials && persona.tutorials.length > 0 ? (
                <TutorialsDetails
                  tutorials={persona.tutorials}
                  render={(tutorial, isSelected) => (
                    <div className="tutorial-summary">
                      <h3>{tutorial.title}</h3>
                      <p>{tutorial.published ? "Publicado" : "No Publicado"}</p>
                      {isSelected && (
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
                    </div>
                  )}
                />
              ) : (
                <p className="no-tutorials">No hay tutoriales disponibles.</p>
              )}
            </div>
          </div>
        </div>
      ) : (
        <div className="loading-message">
          <p>Selecciona una persona...</p>
        </div>
      )}
    </div>
  );
}

export default AgendaDetails;