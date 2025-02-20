import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import productoService from "../service/producto.service";

function ProductoAdd() {
  const [newProducto, setNewProducto] = useState({
    name: "",
    brand: "",
    stock: 0,
    precio: 0.0,
    active: false,
    unidades: 0,
  });

  const navegar = useNavigate();

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setNewProducto((prevState) => ({
      ...prevState,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const createProducto = () => {
    productoService
      .create({ ...newProducto })
      .then(() => {
        navegar("/productos");
      })
      .catch((e) => {
        console.log(e);
      });
  };

  const cancelar = () => {
    navegar("/productos");
  };

  return (
    <div className="container mt-4">
      <h2>Agregar Nuevo Producto</h2>
      <form>
        <div className="mb-3">
          <label className="form-label">Nombre:</label>
          <input
            type="text"
            className="form-control"
            name="name"
            value={newProducto.name}
            onChange={handleChange}
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Marca:</label>
          <input
            type="text"
            className="form-control"
            name="brand"
            value={newProducto.brand}
            onChange={handleChange}
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Stock:</label>
          <input
            type="number"
            className="form-control"
            name="stock"
            value={newProducto.stock}
            onChange={handleChange}
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Precio (€):</label>
          <input
            type="number"
            step="0.01"
            className="form-control"
            name="precio"
            value={newProducto.precio}
            onChange={handleChange}
            required
          />
        </div>

        <div className="mb-3 form-check">
          <input
            type="checkbox"
            className="form-check-input"
            name="active"
            checked={newProducto.active}
            onChange={handleChange}
          />
          <label className="form-check-label">Activo</label>
        </div>

        <button type="button" className="btn btn-primary me-2" onClick={createProducto}>
          Guardar
        </button>
        <button type="button" className="btn btn-secondary" onClick={cancelar}>
          Cancelar
        </button>
      </form>
    </div>
  );
}

export default ProductoAdd;
