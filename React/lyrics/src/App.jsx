import { useState } from "react";
import "./App.css";
import Lyrics from "./components/Lyrics";
import SongLyrics from "./components/SongLyrics";

function App() {
  const [song, setSong] = useState("");
  const [artist, setArtist] = useState("");
  const [canciones, setCanciones] = useState([]); // Estado para almacenar el historial de canciones

  // Función para obtener los datos
  const nameSong = (name) => {
    setSong(name);
  };

  const nameArtist = (name) => {
    setArtist(name);
  };

  const buscar = () => {
    const formattSong = encodeURIComponent(song);
    const formattArtist = encodeURIComponent(artist);
    console.log("Cancion --> ", formattSong);
    fetch(`https://api.lyrics.ovh/v1/${formattArtist}/${formattSong}`)
      .then((response) => {
        if (response.ok) {
          return response.json();
        } else {
          throw new Error(response.statusText);
        }
      })
      .then((data) => {
        // Guardar la canción en el historial
        setCanciones((prevCanciones) => [
          ...prevCanciones,
          { song, artist, lyrics: data.lyrics },
        ]);
      })
      .catch((error) => {
        console.error(error);
        alert("No se ha encontrado la canción.");
      });
  };

  return (
    <>
      <Lyrics nameSong={nameSong} nameArtist={nameArtist} buscar={buscar} />
      <SongLyrics canciones={canciones} />
    </>
  );
}

export default App;
