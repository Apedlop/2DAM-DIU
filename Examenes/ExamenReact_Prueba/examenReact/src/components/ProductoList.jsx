import React, { useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import productoService from "../service/producto.service";
import { Link } from "react-router-dom";
import ProductoCompra from "./ProductoCompra";
import { ProgressBar } from "react-bootstrap";

function ProductoList() {
  const [productos, setProductos] = useState([]);
  const [selectProducto, setSelectProducto] = useState(null);
  const [idSelect, setIdSelect] = useState(-1);
  const MAX_PRODUCTOS = 5; // Límite máximo de productos

  useEffect(() => {
    getAllProductos();
  }, []);

  const getAllProductos = () => {
    productoService
      .getAll()
      .then((response) => {
        setProductos(response.data);
      })
      .catch((e) => {
        console.log(e);
      });
  };

  const setActiveProducto = (producto, index) => {
    setSelectProducto(producto);
    setIdSelect(index);
  };

  const refreshList = () => {
    getAllProductos();
    setSelectProducto(null);
    setIdSelect(-1);
  }

  const removeAllProductos = () => {
    productoService
      .removeAll()
      .then(() => {
        setProductos([]);
        setSelectProducto(null);
        setIdSelect(-1);
      })
      .catch((e) => {
        console.log(e);
      });
  };

  // Función para obtener el porcentaje de productos
  const obtenerPorcentajeProductos = () => {
    return (productos.length / MAX_PRODUCTOS) * 100;
  };

    // Función para obtener el color de la barra de progreso
    const obtenerColorBarra = () => {
        const porcentaje = obtenerPorcentajeProductos();
        if (porcentaje <= 20) {
          return "danger"; // Rojo
        } else if (porcentaje <= 50) {
          return "warning"; // Amarillo
        } else {
          return "success"; // Verde
        }
      };

  return (
    <div className="container mt-4">
          {/* Barra de progreso de Bootstrap */}
          <div className="mt-3">
            <ProgressBar
              now={obtenerPorcentajeProductos()}
              label={`${productos.length} / ${MAX_PRODUCTOS}`}
              variant={obtenerColorBarra()}
              animated
              style={{ height: "20px" }}
            />
          </div>

          {/* Verifica si se alcanzó el máximo de productos */}
          {productos.length >= MAX_PRODUCTOS ? (
            <div className="alert alert-danger mt-3">
              No puedes añadir más productos. El límite es {MAX_PRODUCTOS} productos.
            </div>
          ) : (
            <button className="btn btn-sm btn-danger mt-3" onClick={removeAllProductos}>
              Eliminar Todo
            </button>
          )}
      <div className="row">
        <div className="col-md-6">
          <h4>Lista de Productos</h4>
          <table className="table table-striped">
            <thead>
              <tr>
                <th>#</th>
                <th>Nombre</th>
                <th>Marca</th>
              </tr>
            </thead>
            <tbody>
              {productos.map((producto, index) => (
                <tr
                  key={index}
                  className={index === idSelect ? "table-active" : ""}
                  onClick={() => setActiveProducto(producto, index)}
                >
                  <td>{index + 1}</td>
                  <td>{producto.name}</td>
                  <td>{producto.brand}</td>
                </tr>
              ))}
            </tbody>
          </table>

          <button className="btn btn-sm btn-danger mt-3" onClick={removeAllProductos}>
            Eliminar Todo
          </button>
        </div>

        <div className="col-md-6">
          {selectProducto ? (
            <div>
              <h4>Detalles del Producto</h4>
              <p><strong>Nombre:</strong> {selectProducto.name}</p>
              <p><strong>Stock:</strong> {selectProducto.stock}</p>
              <p><strong>Precio:</strong> {selectProducto.precio}€</p>
              <p><ProductoCompra
                id={selectProducto.id}
                actualizarStock={refreshList} // Pasar la función
              />
              </p>
              <Link to={`/productos/${selectProducto.id}`} className="btn btn-warning btn-sm">
                Editar
              </Link>
            </div>
          ) : (
            <div>
              <br />
              <p>Haz clic en un producto para ver los detalles...</p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default ProductoList;
