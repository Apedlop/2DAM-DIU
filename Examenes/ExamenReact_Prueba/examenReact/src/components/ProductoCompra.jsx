import React, { useState } from "react";
import productoService from "../service/producto.service";

function ProductoCompra(props) {
  const { producto, actualizarStock } = props; // Recibe el producto seleccionado y la función para actualizar el stock
  const [total, setTotal] = useState(0);
  const [unidades, setUnidades] = useState(0);

  // Maneja el cambio en la cantidad de unidades ingresadas
  const getUnidades = (e) => {
    const unidadesIngresadas = parseInt(e.target.value, 10) || 0; // Convertir a número
    if (!producto) return;

    if (unidadesIngresadas <= producto.stock && unidadesIngresadas >= 0) {
      setUnidades(unidadesIngresadas);
      setTotal(unidadesIngresadas * producto.price);
    } else {
      alert("No hay suficientes unidades en stock o la cantidad es inválida.");
    }
  };

  // Función para procesar la compra
  const comprar = () => {
    const productoActualizado = {
      ...producto,
      stock: producto.stock - unidades,
    };

    productoService
      .update(producto.id, productoActualizado)
      .then(() => {
        setUnidades(0); // Resetear unidades después de la compra
        actualizarStock(producto.id, unidades);
        alert(
          `Compra realizada con éxito: ${unidades} unidad(es) de ${producto.name}. Total: €${total}`
        );
      })
      .catch((e) => {
        console.log(e);
      });
  };

  // Ejecuta la compra
  const handleCompra = (e) => {
    e.preventDefault();
    if (!producto) {
      alert("Error: No se ha cargado el producto.");
      return;
    }

    if (unidades > 0 && unidades <= producto.stock) {
      comprar(); // Ya validado anteriormente
    } else {
      alert("Por favor ingresa una cantidad válida.");
    }
  };

  // Comprobar el stock y mostrar el mensaje correspondiente
  const comprobarStock = () => {
    if (!producto) return null;
    const { stock } = producto;

    if (stock === 0)
      return <span className="text-danger">Producto agotado</span>;
    if (stock <= 10) return <span className="text-warning">¡Stock bajo!</span>;
    return <span className="text-success">Stock disponible</span>;
  };

  return (
    <div>
      {producto ? (
        producto.active ? ( // Si el producto está activo, muestra el formulario
          <div>
            <div>{comprobarStock()}</div>
            <form onSubmit={handleCompra}>
              <div>
                <label>Unidades a comprar:</label>
                <input
                  type="number"
                  min="0"
                  value={unidades}
                  onChange={getUnidades}
                  className="form-control"
                />
              </div>
              <div>
                {unidades > 0 && (
                  <div className="mt-3">
                    <strong>Total a pagar: €{total}</strong>
                  </div>
                )}
                <button
                  className="btn btn-success mt-3"
                  type="submit"
                  disabled={unidades <= 0 || unidades > producto.stock}
                >
                  Comprar
                </button>
              </div>
            </form>
          </div>
        ) : (
          <p className="text-danger">
            Este producto no está disponible para la venta.
          </p>
        )
      ) : (
        <p>Cargando producto...</p>
      )}
    </div>
  );
}

export default ProductoCompra;
