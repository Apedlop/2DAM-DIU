import React, { useState } from "react";
import "./App.css";
import Calculadora from "./components/Calculadora";
import * as math from "mathjs"; // Asegúrate de importar mathjs

function App() {
  const [dato, setDato] = useState("");
  const [verDato, setVerDato] = useState("");
  const [actualizar, setActualizar] = useState(false);

  // Función que maneja el click en los botones
  const click = (nombreBoton) => {
    if (actualizar) {
      if (isNaN(nombreBoton)) {
        // Si el botón es un operador, continúa la operación con el resultado anterior
        setDato(verDato + nombreBoton);
        setVerDato(verDato + nombreBoton); // Mostrar también el operador en la pantalla
        setActualizar(false); // Desactiva la necesidad de reiniciar
      } else {
        // Si es un número, reinicia la operación
        setDato(nombreBoton);
        setVerDato(nombreBoton); // Reiniciar la pantalla con el nuevo número
        setActualizar(false);
      }
    } else {
      // Continúa concatenando el número o operador
      if (isNaN(nombreBoton)) {
        setDato((prev) => prev + nombreBoton); // Concatenamos el operador a dato
        setVerDato((prev) => prev + nombreBoton); // Concatenamos el operador a la pantalla
      } else {
        setDato((prev) => prev + nombreBoton); // Concatenamos el número a dato
        setVerDato((prev) => prev + nombreBoton); // Concatenamos el número a la pantalla
      }
    }
  };

  // Limpia la pantalla y restablece el dato
  const limpiar = () => {
    setVerDato("");
    setDato("");
  };

  // Hace la operación
  const calcular = () => {
    try {
      const resultado = math.evaluate(dato);
      setVerDato(resultado);
      setDato(resultado.toString());
      setActualizar(true); // Permite que la próxima entrada reinicie la cuenta
    } catch (error) {
      setVerDato("SyntaxError");
    }
  };

  // Para cambiar el signo
  const cambio = () => {
    setVerDato((prev) => prev * -1);
  };

  return (
    <>
      <Calculadora
        click={click}
        limpiar={limpiar}
        calcular={calcular}
        cambio={cambio}
        verDato={verDato}
      />
    </>
  );
}

export default App;
