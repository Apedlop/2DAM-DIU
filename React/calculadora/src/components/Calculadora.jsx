import React from "react";
import "../style/Calculadora.css"; // Agregar archivo de CSS

function Calculadora() {
  return (
    <div className="calculadora-container">
      <div className="pantalla">0</div>
      <div className="calculadora">
        <button className="btn">C</button>
        <button className="btn">÷</button>
        <button className="btn">^</button>
        <button className="btn">-</button>

        <button className="btn">7</button>
        <button className="btn">8</button>
        <button className="btn">9</button>
        <button className="btn plus" rowSpan={2}>
          +
        </button>

        <button className="btn">4</button>
        <button className="btn">5</button>
        <button className="btn">6</button>

        <button className="btn">1</button>
        <button className="btn">2</button>
        <button className="btn">3</button>
        <button className="btn equals" rowSpan={2}>
          =
        </button>

        <button className="btn cero" colSpan={2}>
          0
        </button>
        <button className="btn">.</button>
      </div>
    </div>
  );
}

export default Calculadora;
