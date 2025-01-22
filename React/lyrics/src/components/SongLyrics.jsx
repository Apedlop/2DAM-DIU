import React from "react";
import "../style/style.css";
import "bootstrap/dist/css/bootstrap.min.css";

function SongLyrics(props) {
  const { canciones } = props;

  return (
    <div className="container mt-4">
      <h2 className="text-center">Historial de Canciones</h2>
      <table className="table">
        <thead>
          <tr>
            <th>Título</th>
            <th>Artista</th>
            <th>Letra</th>
          </tr>
        </thead>
        <tbody>
          {canciones.length > 0 ? (
            [...canciones].reverse().map((cancion, index) => (
              <tr key={index}>
                <td>{cancion.song}</td>
                <td>{cancion.artist}</td>
                <td>
                  <pre>{cancion.lyrics}</pre>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="3" className="text-center">No hay canciones disponibles</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}

export default React.memo(SongLyrics);
