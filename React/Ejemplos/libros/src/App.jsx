import React, { useState } from "react";
import LibrosAdd from "./components/LibrosAdd";
import EstudianteAdd from "./components/EstudianteAdd";


const App = () => {
  const [estudiante, setEstudiante] = useState("");
  const [libros, setLibros] = useState([]);

  const guardarTodo = () => {
    if (!estudiante || libros.length === 0) {
      alert(
        "Por favor, completa el nombre del estudiante y agrega al menos un libro."
      );
      return;
    }

    const datosAGuardar = {
      estudiante,
      libros,
    };

    console.log("Guardando datos:", datosAGuardar);
    alert("¡Estudiante y libros guardados!");
  };

  return (
    <div>
      <h1>Estudiante y Libros</h1>
      <EstudianteAdd setEstudiante={setEstudiante} />
      <LibrosAdd setLibros={setLibros} />

      <button onClick={guardarTodo}>Guardar Todo</button>

      {estudiante && libros.length > 0 && (
        <div>
          <h3>Resumen:</h3>
          <p>📚 {estudiante} está leyendo:</p>
          <ul>
            {libros.map((libro, index) => (
              <li key={index}>
                "{libro.titulo}" de {libro.autor}
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default App;
