import React, { useState } from "react";
import * as math from "mathjs";
import "../style/Calculadora.css";
import "bootstrap/dist/css/bootstrap.min.css";

function Calculadora(props) {

  const { click, limpiar, calcular, cambio, dato} = props;;

  return (
    <div className="container-fluid">
      <div className="calculadora text-center">
        {/* Pantalla */}
        <div className="display">{dato || "0"}</div>
        {/* Botones */}
        <div className="row g-2">
          {/* Primera fila */}
          <div className="col-3">
            <button className="btn btn-danger w-100" onClick={limpiar}>
              AC
            </button>
          </div>
          <div className="col-3">
            <button className="btn btn-purple w-100" onClick={cambio}>
              +/-
            </button>
          </div>
          <div className="col-3">
            <button className="btn btn-purple w-100" onClick={() => click("%")}>
              %
            </button>
          </div>
          <div className="col-3">
            <button className="btn btn-purple w-100" onClick={() => click("/")}>
              ÷
            </button>
          </div>

          {/* Segunda fila */}
          <div className="col-3">
            <button className="btn btn-gray w-100" onClick={() => click(7)}>
              7
            </button>
          </div>
          <div className="col-3">
            <button className="btn btn-gray w-100" onClick={() => click(8)}>
              8
            </button>
          </div>
          <div className="col-3">
            <button className="btn btn-gray w-100" onClick={() => click(9)}>
              9
            </button>
          </div>
          <div className="col-3">
            <button className="btn btn-purple w-100" onClick={() => click("*")}>
              x
            </button>
          </div>

          {/* Tercera fila */}
          <div className="col-3">
            <button className="btn btn-gray w-100" onClick={() => click(4)}>
              4
            </button>
          </div>
          <div className="col-3">
            <button className="btn btn-gray w-100" onClick={() => click(5)}>
              5
            </button>
          </div>
          <div className="col-3">
            <button className="btn btn-gray w-100" onClick={() => click(6)}>
              6
            </button>
          </div>
          <div className="col-3">
            <button className="btn btn-purple w-100" onClick={() => click("-")}>
              -
            </button>
          </div>

          {/* Cuarta fila */}
          <div className="col-3">
            <button className="btn btn-gray w-100" onClick={() => click(1)}>
              1
            </button>
          </div>
          <div className="col-3">
            <button className="btn btn-gray w-100" onClick={() => click(2)}>
              2
            </button>
          </div>
          <div className="col-3">
            <button className="btn btn-gray w-100" onClick={() => click(3)}>
              3
            </button>
          </div>
          <div className="col-3">
            <button className="btn btn-purple w-100" onClick={() => click("+")}>
              +
            </button>
          </div>

          {/* Quinta fila */}
          <div className="col-6">
            <button className="btn btn-gray w-100" onClick={() => click(0)}>
              0
            </button>
          </div>
          <div className="col-3">
            <button className="btn btn-gray w-100" onClick={() => click(".")}>
              .
            </button>
          </div>
          <div className="col-3">
            <button className="btn btn-purple w-100" onClick={calcular}>
              =
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Calculadora;
