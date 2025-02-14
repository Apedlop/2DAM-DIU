import React, { useState } from "react";

const LibrosAdd = ({ setLibros }) => {
  const [nuevoLibro, setNuevoLibro] = useState({
    titulo: "",
    autor: "",
  });
  const [listaLibros, setListaLibros] = useState([]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNuevoLibro((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const agregarLibro = () => {
    if (!nuevoLibro.titulo || !nuevoLibro.autor) {
      alert("Por favor, completa el título y el autor del libro.");
      return;
    }

    const nuevosLibros = [...listaLibros, nuevoLibro];
    setListaLibros(nuevosLibros);
    setLibros(nuevosLibros);

    // Limpiar campos
    setNuevoLibro({ titulo: "", autor: "" });
  };

  return (
    <div>
      <h2>Agregar Libros</h2>
      <input
        type="text"
        name="titulo"
        placeholder="Título del libro"
        value={nuevoLibro.titulo}
        onChange={handleChange}
      />
      <input
        type="text"
        name="autor"
        placeholder="Autor del libro"
        value={nuevoLibro.autor}
        onChange={handleChange}
      />
      <button onClick={agregarLibro}>Agregar Libro</button>

      <h4>Libros Agregados:</h4>
      <ul>
        {listaLibros.map((libro, index) => (
          <li key={index}>
            "{libro.titulo}" de {libro.autor}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default LibrosAdd;
