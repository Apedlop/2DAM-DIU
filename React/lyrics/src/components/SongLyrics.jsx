import React from "react";
import "../style/style.css";

function SongLyrics(props) {
  const { songLyrics } = props;

  return <div className="formatoLetra">{songLyrics}</div>;
}

export default SongLyrics;
