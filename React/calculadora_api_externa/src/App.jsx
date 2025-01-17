import React, { useState } from "react";
import "./App.css";
import Calculadora from "./components/Calculadora";

function App() {
  const [dato, setDato] = useState("");
  const [actualizar, setActualizar] = useState(false);

  // Función que maneja el click en los botones
  const click = (nombreBoton) => {
    if (actualizar) {
      if (isNaN(nombreBoton)) {
        // Si el botón es un operador, continúa la operación con el resultado anterior
        setDato((prev) => prev + nombreBoton);
        setActualizar(false); // Desactiva la necesidad de reiniciar
      } else {
        // Si es un número, reinicia la operación
        setDato(nombreBoton);
        setActualizar(false);
      }
    } else {
      // Continúa concatenando el número o operador
      if (isNaN(nombreBoton)) {
        setDato((prev) => prev + nombreBoton); // Concatenamos el operador a dato
      } else {
        setDato((prev) => prev + nombreBoton); // Concatenamos el número a dato
      }
    }
  };

  // Limpia la pantalla y restablece el dato
  const limpiar = () => {
    setDato("");
  };

  // Hace la operación
  const calcular = () => {
    // encodeURIComponent sirve para codificar caracteres especiales en una cadena, haciéndola segura para usarse como parte de una URL
    const operacion = encodeURIComponent(dato.trim);
    fetch(`http://api.mathjs.org/v4/?expr=${operacion}`)
      .then((response) => {
        if (response.ok) {
          return response.text(); // Obtener la respuesta como texto
        } else {
          throw new Error(response.statusText);
        }
      })
      .then((resultado) => {
        setDato(resultado); // Actualizar el estado con el resultado
        setActualizar(true);
      });
  };

  // Para cambiar el signo
  const cambio = () => {
    setDato((prev) => prev * -1);
  };

  return (
    <>
      <Calculadora
        click={click}
        limpiar={limpiar}
        calcular={calcular}
        cambio={cambio}
        dato={dato}
      />
    </>
  );
}

export default App;
