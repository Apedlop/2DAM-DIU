import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import productoService from "../service/producto.service";
import { ProgressBar } from "react-bootstrap";

function ProductoAdd() {
  const [newProducto, setNewProducto] = useState({
    stock: 0,
    name: "",
    brand: "",
    price: 0.0,
    active: false,
  });

  const [productos, setProductos] = useState([]);
  const navegar = useNavigate();
  const MAX_PRODUCTOS = 5; // Límite máximo de productos

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

  // Efecto para cargar los productos al inicio
  useEffect(() => {
    productoService
      .getAll() // Suponiendo que tienes un método getAll en el servicio
      .then((response) => {
        setProductos(response.data);
      })
      .catch((e) => {
        console.log(e);
      });
  }, []);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setNewProducto((prevState) => ({
      ...prevState,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const createProducto = () => {
    if (productos.length >= MAX_PRODUCTOS) return; // Prevenir creación si ya se alcanzó el límite
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
      {/* Barra de progreso de Bootstrap */}
      <div className="mt-3">
        <ProgressBar
          now={obtenerPorcentajeProductos()}
          label={`${Math.round(obtenerPorcentajeProductos())}%`} // Muestra el porcentaje redondeado
          variant={obtenerColorBarra()}
          animated
          style={{ height: "20px", width: "50%", margin: "auto" }}
        />
      </div>

      {/* Verifica si se alcanzó el máximo de productos */}
      {productos.length >= MAX_PRODUCTOS ? (
        <div className="alert alert-danger mt-3">
          No puedes añadir más productos. El límite es {MAX_PRODUCTOS}{" "}
          productos.
        </div>
      ) : (
        <br />
      )}

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
            name="price"
            value={newProducto.price}
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

        <button
          type="button"
          className="btn btn-primary me-2"
          onClick={createProducto}
          disabled={productos.length >= MAX_PRODUCTOS} // Deshabilita si se ha alcanzado el límite
        >
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
