import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";

function Lyrics(props) {
  const { nameSong, nameArtist, buscar } = props;

  const songChange = (e) => {
    nameSong(e.target.value);
  };

  const artistChange = (e) => {
    nameArtist(e.target.value);
  };

  return (
    <div>
      <h1>Búsqueda canciones</h1>
      <form action="">
        <label htmlFor="Song">Canción: </label>
        <input
          type="text"
          placeholder="Canción"
          onChange={songChange} // Cambié de onClick a onChange
        />
        <label htmlFor="Artist">Artista: </label>
        <input
          type="text"
          placeholder="Artista"
          onChange={artistChange} // Cambié de onClick a onChange
        />
        <button type="button" onClick={buscar}>
          Buscar
        </button>{" "}
        {/* Cambié a type="button" para evitar el submit por defecto */}
      </form>
    </div>
  );
}

export default Lyrics;
