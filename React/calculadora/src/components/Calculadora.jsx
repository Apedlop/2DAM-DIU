import React, { useState } from "react";
import "../style/Calculadora.css"; // Agregar archivo de CSS
import "bootstrap/dist/css/bootstrap.min.css";
import calculate from "../logic/calculate";

function Calculadora() {
  const [dato, setDato] = useState({ total: null, next: null, operation: null });

  // Función que maneja el click en los botones
  const click = (nombreBoton) => {
    const nuevoDato = calculate(dato, nombreBoton);
    setDato(nuevoDato);
  }
  

  const { total, next, operation } = dato;
  const valor = next || total || "0"; // Para mostrar el valor en la pantalla, en el caso de que no haya "next" o "total"

  return (
    <div className="container-fluid">
      <div className="calculadora text-center">
        {/* Pantalla */}
        <div className="display">{valor}</div>
        {/* Botones */}
        <div className="row g-2">
          {/* Primera fila */}
          <div className="col-3">
            <button className="btn btn-danger w-100" onClick={() => click("AC")}>AC</button>
          </div>
          <div className="col-3">
            <button className="btn btn-purple w-100" onClick={() => click("+/-")}>+/-</button>
          </div>
          <div className="col-3">
            <button className="btn btn-purple w-100" onClick={() => click("%")}>%</button>
          </div>
          <div className="col-3">
            <button className="btn btn-purple w-100" onClick={() => click("÷")}>÷</button>
          </div>

          {/* Segunda fila */}
          <div className="col-3">
            <button className="btn btn-light w-100" onClick={() => click("7")}>7</button>
          </div>
          <div className="col-3">
            <button className="btn btn-light w-100" onClick={() => click("8")}>8</button>
          </div>
          <div className="col-3">
            <button className="btn btn-light w-100" onClick={() => click("9")}>9</button>
          </div>
          <div className="col-3">
            <button className="btn btn-purple w-100" onClick={() => click("x")}>x</button>
          </div>

          {/* Tercera fila */}
          <div className="col-3">
            <button className="btn btn-light w-100" onClick={() => click("4")}>4</button>
          </div>
          <div className="col-3">
            <button className="btn btn-light w-100" onClick={() => click("5")}>5</button>
          </div>
          <div className="col-3">
            <button className="btn btn-light w-100" onClick={() => click("6")}>6</button>
          </div>
          <div className="col-3">
            <button className="btn btn-purple w-100" onClick={() => click("-")}>-</button>
          </div>

          {/* Cuarta fila */}
          <div className="col-3">
            <button className="btn btn-light w-100" onClick={() => click("1")}>1</button>
          </div>
          <div className="col-3">
            <button className="btn btn-light w-100" onClick={() => click("2")}>2</button>
          </div>
          <div className="col-3">
            <button className="btn btn-light w-100" onClick={() => click("3")}>3</button>
          </div>
          <div className="col-3">
            <button className="btn btn-purple w-100" onClick={() => click("+")}>+</button>
          </div>

          {/* Quinta fila */}
          <div className="col-6">
            <button className="btn btn-light w-100" onClick={() => click("0")}>0</button>
          </div>
          <div className="col-3">
            <button className="btn btn-light w-100" onClick={() => click(".")}>.</button>
          </div>
          <div className="col-3">
            <button className="btn btn-purple w-100" onClick={() => click("=")}>=</button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Calculadora;
