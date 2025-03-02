import React, { useState, useEffect, useContext } from "react";
import TutorialDataService from "../service/tutorials.service";
import { Link } from "react-router-dom";
import { FaPlus, FaEdit, FaTrash } from "react-icons/fa"; // Iconos
import { UserContext } from "../provider/UserProvider";
import "./style/TutorialsList.css"; // Importa estilos

const TutorialsList = () => {
  const { user } = useContext(UserContext); // Usuario autenticado
  const [tutorials, setTutorials] = useState([]); // Lista de tutoriales
  const [selectedTutorial, setSelectedTutorial] = useState(null); // Tutorial expandido
  const [searchTitle, setSearchTitle] = useState("");

  useEffect(() => {
    retrieveTutorials();
  }, []);

  const retrieveTutorials = () => {
    TutorialDataService.getAll()
      .then((response) => setTutorials(response.data))
      .catch((e) => console.log(e));
  };

  const searchTitleHandler = () => {
    TutorialDataService.findByTitle(searchTitle)
      .then((response) => setTutorials(response.data))
      .catch((e) => console.log(e));
  };

  const handleDelete = (id) => {
    TutorialDataService.delete(id)
      .then(() => {
        setTutorials(tutorials.filter((t) => t.id !== id));
        setSelectedTutorial(null);
      })
      .catch((e) => console.log(e));
  };

  return (
    <div className="tutorials-list-container">

      {/* Barra de búsqueda */}
      <div className="search-section">
        <div className="input-group mb-3">
          <input
            type="text"
            className="form-control"
            placeholder="Buscar por título"
            value={searchTitle}
            onChange={(e) => setSearchTitle(e.target.value)}
          />
          <div className="input-group-append">
            <button className="btn btn-secondary" onClick={searchTitleHandler}>
              <i className="fas fa-search"></i> Buscar
            </button>
          </div>
        </div>
      </div>

      <div className="header">
        <h2 className="tutoriales-title titulo">Lista de Tutoriales</h2>
      </div>

      {/* Tabla de tutoriales */}
      <table>
        <thead>
          <tr>
            <th>Imagen</th>
            <th>Nombre</th>
            <th>Publicado</th>
          </tr>
        </thead>
        <tbody>
          {tutorials.length > 0 ? (
            tutorials.map((tutorial, index) => (
              <React.Fragment key={index}>
                <tr
                  className={`tutorial-row ${
                    selectedTutorial === tutorial ? "active" : ""
                  }`}
                  onClick={() =>
                    setSelectedTutorial(
                      selectedTutorial === tutorial ? null : tutorial
                    )
                  }
                >
                  {/* Imagen */}
                  <td>
                    {tutorial.imagen && (
                      <img
                        src={tutorial.imagen}
                        alt={tutorial.title}
                        className="tutorial-img"
                      />
                    )}
                  </td>
                  <td>{tutorial.title}</td>
                  <td>{tutorial.published ? "Sí" : "No"}</td>
                </tr>

                {/* Descripción y botones, solo si está seleccionado */}
                {selectedTutorial === tutorial && (
                  <tr className="expanded-row">
                    <td colSpan="3">
                      <p className="tutorial-description">{tutorial.description}</p>

                      {user && (
                        <div className="tutorial-actions">
                          <Link
                            to={`/tutorials/edit/${tutorial.id}`}
                            className="btn btn-warning"
                          >
                            <FaEdit /> Editar
                          </Link>
                          <button
                            className="btn btn-danger"
                            onClick={() => handleDelete(tutorial.id)}
                          >
                            <FaTrash /> Eliminar
                          </button>
                        </div>
                      )}
                    </td>
                  </tr>
                )}
              </React.Fragment>
            ))
          ) : (
            <tr>
              <td colSpan="3" className="text-center">
                No hay tutoriales disponibles...
              </td>
            </tr>
          )}
        </tbody>
      </table>

      {/* Botón flotante para agregar nuevos tutoriales */}
      {user && (
        <Link to="/addTutorials">
          <button className="btn btn-primary boton-flotante">
            <FaPlus />
          </button>
        </Link>
      )}
    </div>
  );
};

export default TutorialsList;
