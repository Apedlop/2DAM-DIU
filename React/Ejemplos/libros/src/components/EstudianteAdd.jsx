import React from "react";

const EstudianteAdd = ({ setEstudiante }) => {
  const handleChange = (e) => {
    setEstudiante(e.target.value);
  };

  return (
    <div>
      <h2>Agregar Estudiante</h2>
      <input
        type="text"
        placeholder="Nombre del estudiante"
        onChange={handleChange}
      />
    </div>
  );
};

export default EstudianteAdd;
