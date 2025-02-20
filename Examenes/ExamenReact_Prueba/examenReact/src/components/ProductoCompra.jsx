import React, { useState, useEffect } from "react";
import productoService from "../service/producto.service";

function ProductoCompra(props) {
  const { id, actualizarStock } = props; // Recibe la función para actualizar el stock
  const [producto, setProducto] = useState(null);
  const [total, setTotal] = useState(0);
  const [unidades, setUnidades] = useState(0);

  // Obtiene el producto por su id
  useEffect(() => {
    getProducto(id);
  }, [id]);

  const getProducto = (id) => {
    productoService
      .get(id)
      .then((response) => {
        setProducto(response.data);
      })
      .catch((e) => {
        console.log(e);
      });
  };

  // Maneja el cambio en la cantidad de unidades ingresadas
  const getUnidades = (e) => {
    const unidadesIngresadas = e.target.value;
    if (
      producto &&
      unidadesIngresadas <= producto.stock &&
      unidadesIngresadas >= 0
    ) {
      setUnidades(unidadesIngresadas);
      setTotal(unidadesIngresadas * producto.precio);
    } else {
      alert("No hay suficientes unidades en stock o la cantidad es inválida.");
    }
  };

  // Función para procesar la compra
  const comprar = () => {
    const productoActualizado = { ...producto, stock: producto.stock - unidades };
    
    productoService
      .update(producto.id, productoActualizado) // Pasa el id y los datos actualizados
      .then(() => {
        setProducto((prevProducto) => ({
          ...prevProducto,
          stock: prevProducto.stock - unidades,
        }));
        actualizarStock(producto.id, unidades); // Actualiza el stock en el componente padre
        alert(`Compra realizada con éxito: ${unidades} unidad(es) de ${producto.name}. Total: €${total}`);
      })
      .catch((e) => {
        console.log(e);
      });
  };

  // Ejecuta la compra
  const handleCompra = (e) => {
    e.preventDefault();
    if (producto && unidades > 0 && unidades <= producto.stock) {
      comprar(); // Llama a la función comprar
    } else {
      alert("Por favor ingresa una cantidad válida.");
    }
  };

  // Comprobar el stock y mostrar el mensaje correspondiente
  const comprobarStock = () => {
    if (producto) {
      if (producto.stock === 0) {
        return <span className="text-danger">Producto agotado</span>;
      } else if (producto.stock <= 10) {
        return <span className="text-warning">¡Stock bajo!</span>;
      } else {
        return <span className="text-success">Stock disponible</span>;
      }
    }
    return null;
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
          // Si el producto no está activo, muestra un mensaje
          <p className="text-danger">Este producto no está disponible para la venta.</p>
        )
      ) : (
        <p>Cargando producto...</p>
      )}
    </div>
  );
}

export default ProductoCompra;
