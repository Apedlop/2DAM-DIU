import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";

function App() {
  // Estado para la cantidad ingresada por el usuario
  const [cantidad, setCantidad] = useState("");
  // Estado para la moneda seleccionada
  const [moneda, setMoneda] = useState("USD");
  // Estado para almacenar el historial de conversiones
  const [historial, setHistorial] = useState([]);
  // Estado para manejar la animación de carga
  const [cargando, setCargando] = useState(false);

  // Tasas de conversión simuladas a Euro (€)
  const tasasDeCambio = {
    USD: 0.93, // 1 USD = 0.93 EUR
    GBP: 1.17, // 1 GBP = 1.17 EUR
    JPY: 0.0063, // 1 JPY = 0.0063 EUR
    MXN: 0.052, // 1 MXN = 0.052 EUR
  };

  // Función para realizar la conversión de moneda
  const convertirMoneda = (e) => {
    e.preventDefault(); // Evita que el formulario recargue la página

    // Validar que la cantidad ingresada sea mayor que 0
    if (cantidad <= 0) {
      alert("Por favor, ingresa una cantidad válida.");
      return;
    }

    // Activar la animación de carga
    setCargando(true);

    // Simulación de un proceso de conversión con un pequeño retraso
    setTimeout(() => {
      const tasa = tasasDeCambio[moneda]; // Obtener la tasa de conversión de la moneda seleccionada
      const resultado = (cantidad * tasa).toFixed(2); // Calcular el valor en euros con dos decimales

      // Actualizar el historial con la nueva conversión
      setHistorial([...historial, { id: historial.length + 1, cantidad, moneda, resultado }]);

      // Desactivar la animación de carga
      setCargando(false);
      // Resetear el campo de cantidad
      setCantidad("");
    }, 1500);
  };

  return (
    <div className="container mt-4">
      <h1 className="text-center">Conversor de Monedas a Euro (€)</h1>

      {/* Formulario para ingresar la cantidad y seleccionar la moneda */}
      <form onSubmit={convertirMoneda} className="card p-4 shadow">
        <div className="mb-3">
          <label className="form-label">Cantidad:</label>
          <input
            type="number"
            className="form-control"
            value={cantidad}
            onChange={(e) => setCantidad(e.target.value)}
            min="0"
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Moneda de Origen:</label>
          <select className="form-select" value={moneda} onChange={(e) => setMoneda(e.target.value)}>
            {/* Itera sobre las monedas disponibles en el objeto tasasDeCambio */}
            {Object.keys(tasasDeCambio).map((codigo) => (
              <option key={codigo} value={codigo}>
                {codigo}
              </option>
            ))}
          </select>
        </div>

        <button type="submit" className="btn btn-primary w-100">Convertir</button>
      </form>

      {/* Barra de progreso (se muestra solo mientras se realiza la conversión) */}
      {cargando && (
        <div className="mt-3">
          <div className="progress">
            <div className="progress-bar progress-bar-striped progress-bar-animated" style={{ width: "100%" }}>
              Convirtiendo...
            </div>
          </div>
        </div>
      )}

      {/* Sección del historial de conversiones */}
      <h2 className="mt-4">Historial de Conversiones</h2>
      {historial.length === 0 ? (
        <p>No hay conversiones aún.</p>
      ) : (
        <table className="table table-bordered mt-3">
          <thead className="table-dark">
            <tr>
              <th>#</th>
              <th>Cantidad</th>
              <th>Moneda</th>
              <th>Resultado (€)</th>
            </tr>
          </thead>
          <tbody>
            {/* Muestra cada conversión en una fila */}
            {historial.map(({ id, cantidad, moneda, resultado }) => (
              <tr key={id}>
                <td>{id}</td>
                <td>{cantidad}</td>
                <td>{moneda}</td>
                <td>{resultado} €</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default App;
