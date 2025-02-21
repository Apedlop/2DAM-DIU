import { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route, Routes, Link } from "react-router-dom";
import "./App.css";
import ProductoList from "./components/ProductoList";
import ProductoAdd from "./components/ProductoAdd";
import ProductoEdit from "./components/ProductoEdit";
import ProductoCompra from "./components/ProductoCompra";
import { ProgressBar } from "react-bootstrap";

function App() {
  const MAX_PRODUCTOS = 5; // Límite máximo de productos
  const [productos, setProductos] = useState([]);

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
    <div>
      <Router>
        <nav className="navbar navbar-expand navbar-dark bg-dark">
          <Link to={"/products"} className="navbar-brand">
            Productos
          </Link>
          <div className="navbar-nav mr-auto">
            <li className="nav-item">
              <Link to={"/add"} className="nav-link">
                Añadir
              </Link>
            </li>
          </div>
        </nav>
        {/* Barra de progreso de Bootstrap */}
        <div className="mt-3">
          <ProgressBar
            now={obtenerPorcentajeProductos()}
            label={`${productos.length} / ${MAX_PRODUCTOS}`}
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
          <br></br>
        )}
        <Routes>
          <Route path="/" element={<ProductoList />} />
          <Route path="/products" element={<ProductoList />} />
          <Route path="/add" element={<ProductoAdd />} />
          <Route path="/comprar" element={<ProductoCompra />} />
          <Route path="/products/:id" element={<ProductoEdit />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
