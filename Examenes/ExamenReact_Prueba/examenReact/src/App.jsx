import { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route, Routes, Link } from "react-router-dom";
import "./App.css";
import ProductoList from "./components/ProductoList";
import ProductoAdd from "./components/ProductoAdd";
import ProductoEdit from "./components/ProductoEdit";
import ProductoCompra from "./components/ProductoCompra";

function App() {
  return (
    <div>
      <Router>
        <nav className="navbar navbar-expand navbar-dark bg-dark">
          <Link to={"/productos"} className="navbar-brand">
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
        <Routes>
          <Route path="/" element={<ProductoList />} />
          <Route path="/productos" element={<ProductoList />} />
          <Route path="/add" element={<ProductoAdd />} />
          <Route path="/comprar" element={<ProductoCompra />} />
          <Route path="/productos/:id" element={<ProductoEdit />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
