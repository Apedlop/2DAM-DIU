import React, { useEffect, useState } from "react";
import productsService from "../service/monedas.service";
import { ProgressBar } from "react-bootstrap";

function CompraProduct(props) {
  const { moneda } = props;
  const [total, setTotal] = useState(0);
  const [unidades, setUnidades] = useState(0);
  const MAX_MONEDAS = 100;

  // Maneja el cambio en la cantidad de unidades ingresadas
  const getUnidades = (e) => {
    const numero = parseInt(e.target.value, 10) || 0; // Convertir a número
    if (!moneda) return;

    if (numero <= moneda.stock && numero >= 0) {
      setUnidades(numero);
      setTotal(numero * moneda.price);
    } else {
      alert("No hay suficientes unidades en stock o la cantidad es inválida.");
    }
  };

  // Función para procesar el incremento
  const handleIngreso = () => {
    const monedaActualizado = {
      ...moneda,
      stock: moneda.stock + 10,
    };

    productsService
      .update(moneda.id, monedaActualizado)
      .then(() => {
        setUnidades(0); // Resetear unidades después de la compra
        alert("Se ha realizado el incremento");
      })
      .catch((e) => {
        console.log(e);
      });
  };

  const obtenerPorcentaje = () => {
    if (moneda.stock >= 100) {
      return 100;
    } else {
      return (moneda.stock / MAX_MONEDAS) * 100;
    }
  };

  return (
    <div>
      <h2>Detalles de {moneda.name}</h2>
      <div>
        {/* Barra de progreso de Bootstrap */}
        <div className="mt-3">
          <ProgressBar
            now={obtenerPorcentaje()}
            label={`${Math.round(obtenerPorcentaje())}%`} // Muestra el porcentaje redondeado
            animated
            style={{ height: "20px", width: "50%", margin: "auto" }}
          />
        </div>
        <div>
          {moneda.stock >= 100 ? (
            <span className="text-danger">Reserva llena</span>
          ) : (
            <span className="text-success">Reserva no llena</span>
          )}
        </div>
        <div className="conversion">
          <form onSubmit={handleIngreso}>
            <div>
              <div>
                <label>Cantidad:</label>{" "}
                <input
                  type="number"
                  min="0"
                  disabled={moneda.active === false || moneda === ""}
                  value={unidades}
                  onChange={getUnidades}
                  style={{ width: "80px" }}
                />{" "}
              </div>
              <div className="mt-3">
                <label>Equivalencia: </label>{" "}
                <input
                  type="number"
                  value={total}
                  style={{ width: "80px" }}
                  disabled
                />
                €
              </div>
            </div>
            <div>
              <button
                className="btn btn-success mt-3"
                type="submit"
                disabled={
                  moneda.stock > 100 ||
                  moneda.active === false ||
                  moneda === ""
                }
              >
                Incrementar
              </button>
            </div>
            {!moneda.active ? (
              <label className="text-danger">La moneda no está activa</label>
            ) : (
              <br></br>
            )}
          </form>
        </div>
      </div>
    </div>
  );
}

export default CompraProduct;
