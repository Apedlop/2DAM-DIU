import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { useNavigate, useParams } from "react-router-dom";
import productoService from "../service/producto.service";

function ProductoEdit() {
  const { id } = useParams();
  const navegar = useNavigate();

  const [newProducto, setNewProducto] = useState({
    stock: 0,
    name: "",
    brand: "",
    price: 0.0,
    active: false,
  });

  useEffect(() => {
    productoService
      .get(id)
      .then((response) => {
        setNewProducto(response.data);
      })
      .catch((error) => console.log("Error al obtener el producto:", error));
  }, [id]);

  const handleChange = (e) => {
    const { id, value, type, checked } = e.target;

    setNewProducto((prevProducto) => ({
      ...prevProducto,
      [id]:
        type === "checkbox"
          ? checked
          : type === "number"
          ? parseFloat(value) || 0
          : value,
    }));
  };

  const handleSubmit = () => {
    productoService
      .update(id, newProducto)
      .then(() => {
        navegar("/products");
      })
      .catch((error) => console.log("Error al actualizar:", error));
  };

  return (
    <div className="container mt-4">
      <h2>Editar Producto</h2>
      <form>
        <div className="mb-3">
          <label htmlFor="name" className="form-label">
            Nombre:
          </label>
          <input
            type="text"
            className="form-control"
            id="name"
            value={newProducto.name}
            onChange={handleChange}
          />
        </div>

        <div className="mb-3">
          <label htmlFor="brand" className="form-label">
            Marca:
          </label>
          <input
            type="text"
            className="form-control"
            id="brand"
            value={newProducto.brand}
            onChange={handleChange}
          />
        </div>

        <div className="mb-3">
          <label htmlFor="stock" className="form-label">
            Stock:
          </label>
          <input
            type="number"
            className="form-control"
            id="stock"
            value={newProducto.stock}
            onChange={handleChange}
          />
        </div>

        <div className="mb-3">
          <label htmlFor="price" className="form-label">
            Precio:
          </label>
          <input
            type="number"
            step="0.01"
            className="form-control"
            id="price"
            value={newProducto.price}
            onChange={handleChange}
          />
        </div>

        <div className="mb-3 form-check">
          <input
            type="checkbox"
            className="form-check-input"
            id="active"
            checked={newProducto.active}
            onChange={handleChange}
          />
          <label className="form-check-label" htmlFor="active">
            Activo
          </label>
        </div>

        <button
          type="button"
          className="btn btn-primary"
          onClick={handleSubmit}
        >
          Guardar Cambios
        </button>
      </form>
    </div>
  );
}

export default ProductoEdit;
