import React from "react";

function AgendaList() {
  return (
    <div className="list row">
      <div className="col-md-6">
        <h4>Agenda</h4>
        <ul className="list-group">
          <li className="list-group-item">
            <div>
              <strong>Nombre:</strong> Juan
            </div>
            <div>
              <strong>Apellido:</strong> Perez
            </div>
            <div>
              <strong>Telefono:</strong> 123456789
            </div>
          </li>
          <li className="list-group-item">
            <div>
              <strong>Nombre:</strong> Maria
            </div>
            <div>
              <strong>Apellido:</strong> Garcia
            </div>
            <div>
              <strong>Telefono:</strong> 987654321
            </div>
          </li>
        </ul>
      </div>
    </div>
  );
}

export default AgendaList;
