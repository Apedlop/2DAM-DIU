import { useState } from "react";
import "./App.css";
import Lyrics from "./components/Lyrics";

function App() {
  const [song, setSong] = useState(""); // Inicializar como cadena vacía
  const [artist, setArtist] = useState(""); // Inicializar como cadena vacía
  const [songLyrics, setSongLyrics] = useState("");

  // Función para obtener los datos
  const nameSong = (name) => {
    setSong(name);
  };

  const nameArtist = (name) => {
    setArtist(name);
  };

  const buscar = () => {
    if (!song || !artist) {
      alert("Por favor, ingresa el nombre de la canción y el artista.");
      return;
    }

    const formattSong = encodeURIComponent(song);
    const formattArtist = encodeURIComponent(artist);

    fetch(`https://api.lyrics.ovh/v1/${formattArtist}/${formattSong}`)
      .then((response) => {
        if (response.ok) {
          return response.json(); // Cambié a .json() ya que la respuesta es un objeto JSON
        } else {
          throw new Error(response.statusText);
        }
      })
      .then((data) => {
        setSongLyrics(data.lyrics); // Actualiza el estado con la letra
      })
      .catch((error) => {
        console.error(error);
        setSongLyrics("No se encontraron letras para esta canción.");
      });
  };

  return (
    <>
      <Lyrics
        nameSong={nameSong}
        nameArtist={nameArtist}
        buscar={buscar}
        songLyrics={songLyrics}
      />
    </>
  );
}

export default App;
