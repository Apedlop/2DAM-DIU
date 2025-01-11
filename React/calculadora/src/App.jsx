import React, { useState } from "react";
import "./App.css";
import Calculadora from "./components/Calculadora";
import * as math from "mathjs"; // Asegúrate de importar mathjs

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
    try {
      const resultado = math.evaluate(dato);
      setDato(resultado.toString());
      setActualizar(true); // Permite que la próxima entrada reinicie la cuenta
    } catch (error) {
      setDato("SyntaxError");
    }
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
