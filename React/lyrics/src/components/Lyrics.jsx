import React, { useState } from "react";
import "../style/style.css"; // Asegúrate de que esté bien enlazado el CSS
import "bootstrap/dist/css/bootstrap.min.css";

function Lyrics(props) {
  const { nameSong, nameArtist, buscar } = props;
  const [isLoading, setIsLoading] = useState(false); // Estado para saber si está buscando

  const songChange = (e) => {
    nameSong(e.target.value);
  };

  const artistChange = (e) => {
    nameArtist(e.target.value);
  };

  const handleSearch = () => {
    setIsLoading(true); // Al comenzar la búsqueda, se habilita el estado de carga

    // Simula un retraso de 1 segundo antes de buscar la letra
    setTimeout(() => {
      buscar();
      setIsLoading(false); // Cuando termine la búsqueda, deshabilita el estado de carga
    }, 1000); // 1 segundo de retraso
  };

  return (
    <div className="busqueda container">
      <h1 className="titulo text-center my-4">Búsqueda canciones</h1>
      <form action="" className="formulario">
        <div className="form-group">
          <label htmlFor="Song">Canción: </label>
          <input
            type="text"
            className="form-control"
            placeholder="Canción"
            onChange={songChange} // Cambié de onClick a onChange
          />
        </div>
        <div className="form-group">
          <label htmlFor="Artist">Artista: </label>
          <input
            type="text"
            className="form-control"
            placeholder="Artista"
            onChange={artistChange} // Cambié de onClick a onChange
          />
        </div>
        <div className="form-group text-center">
          <button
            type="button"
            className="btn btn-primary"
            onClick={handleSearch} // Llamamos a la nueva función
            disabled={isLoading} // Deshabilitar el botón mientras está buscando
          >
            {isLoading ? "Buscando..." : "Buscar"}{" "}
            {/* Cambio de texto según estado */}
          </button>
        </div>
      </form>
    </div>
  );
}

export default Lyrics;
