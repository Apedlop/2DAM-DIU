import React, { useState, useEffect } from "react";
import monedaService from "../service/monedas.service";
import "bootstrap/dist/css/bootstrap.min.css";
import RetirarMonedas from "./RetirarMonedas";

function ListProducts() {
  const [monedas, setMonedas] = useState([]); // Lista de monedas
  const [selectMoneda, setSelectMoneda] = useState(null);
  const [idSelect, setIdSelect] = useState(-1);

  useEffect(() => {
    getAllMonedas();
  }, []);

  const getAllMonedas = () => {
    monedaService
      .getAll()
      .then((response) => {
        setMonedas(response.data);
      })
      .catch((e) => {
        console.log(e);
      });
  };

  const setActiveProduct = (moneda, index) => {
    setSelectMoneda(moneda);
    setIdSelect(index);
  };

  return (
    <div className="container mt-4">
      {monedas ? (
        <div className="row">
          {/* Lista de monedas */}
          <div className="col-md-6">
            <h2>Lista de monedas</h2>
            <table className="table table-striped">
              <thead>
                <tr>
                  <th>Nombre</th>
                  <th>Descripción</th>
                  <th>Cantidad</th>
                </tr>
              </thead>
              <tbody>
                {monedas.length === 0 ? (
                  <tr>
                    <td colSpan="3" className="text-center">
                      No hay monedas disponibles
                    </td>
                  </tr>
                ) : (
                  monedas.map((moneda, index) => (
                    <tr
                      key={moneda.id}
                      className={index === idSelect ? "table-active" : ""}
                      onClick={() => setActiveProduct(moneda, index)}
                    >
                      <td>{moneda.name}</td>
                      <td>{moneda.brand}</td>
                      <td>{moneda.stock}</td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>

          {/* Detalles del moneda seleccionado */}
          <div className="col-md-6">
            {selectMoneda ? (
              <div>
                  <RetirarMonedas
                    moneda={selectMoneda}
                  />
              </div>
            ) : (
              <div>
                <br />
                <p>Haz clic en un moneda para ver los detalles...</p>
              </div>
            )}
          </div>
        </div>
      ) : (
         <div className="alert alert-warning" role="alert">
            No hay monedas
          </div>
      )}
    </div>
  );
}

export default ListProducts;
