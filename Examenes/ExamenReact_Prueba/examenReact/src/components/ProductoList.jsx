import React, { useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import productoService from "../service/producto.service";
import { Link } from "react-router-dom";
import ProductoCompra from "./ProductoCompra";

function ProductoList() {
  const [productos, setProductos] = useState([]);
  const [selectProducto, setSelectProducto] = useState(null);
  const [idSelect, setIdSelect] = useState(-1);

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
  };

  const removeAllProductos = () => {
    if (
      window.confirm(
        "¿Estás seguro de que deseas eliminar todos los productos?"
      )
    ) {
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
    }
  };

  return (
    <div className="container mt-4">
      <div className="row">
        {/* Lista de productos */}
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
              {productos.length === 0 ? (
                <tr>
                  <td colSpan="3" className="text-center">
                    No hay productos disponibles
                  </td>
                </tr>
              ) : (
                productos.map((producto, index) => (
                  <tr
                    key={producto.id}
                    className={index === idSelect ? "table-active" : ""}
                    onClick={() => setActiveProducto(producto, index)}
                  >
                    <td>{index + 1}</td>
                    <td>{producto.name}</td>
                    <td>{producto.brand}</td>
                  </tr>
                ))
              )}
            </tbody>
          </table>

          <button
            className="btn btn-sm btn-danger mt-3"
            onClick={removeAllProductos}
          >
            Eliminar Todo
          </button>
        </div>

        {/* Detalles del producto seleccionado */}
        <div className="col-md-6">
          {selectProducto ? (
            <div>
              <h4>Detalles del Producto</h4>
              <p>
                <strong>Nombre:</strong> {selectProducto.name}
              </p>
              <p>
                <strong>Stock:</strong> {selectProducto.stock}
              </p>
              <p>
                <strong>Precio:</strong> {selectProducto.price}€
              </p>
              <div>
                <strong>Acción:</strong>
                <ProductoCompra
                  producto={selectProducto}
                  actualizarStock={refreshList}
                />
              </div>
              <Link
                to={`/products/${selectProducto.id}`}
                className="btn btn-warning btn-sm mt-2"
              >
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
