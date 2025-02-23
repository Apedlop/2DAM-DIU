import React, { useState, useEffect } from "react";
import TutorialDataService from "../service/tutorials.service";
import { Link } from "react-router-dom";
import "./style/TutorialsList.css"; // Importa los estilos CSS

const TutorialsList = () => {
  const [tutorials, setTutorials] = useState([]); // Lista de tutoriales
  const [currentTutorial, setCurrentTutorial] = useState(null); // Tutorial seleccionado
  const [searchTitle, setSearchTitle] = useState("");

  // Cuando se monta el componente, se cargan los tutoriales
  useEffect(() => {
    retrieveTutorials();
  }, []);

  const onChangeSearchTitle = (e) => {
    const searchTitle = e.target.value;
    setSearchTitle(searchTitle);
  };

  const retrieveTutorials = () => {
    TutorialDataService.getAll()
      .then((response) => {
        setTutorials(response.data);
        console.log(response.data);
      })
      .catch((e) => {
        console.log(e);
      });
  };

  const refreshList = () => {
    retrieveTutorials();
    setCurrentTutorial(null);
  };

  const removeAllTutorials = () => {
    TutorialDataService.deleteAll()
      .then((response) => {
        console.log(response.data);
        refreshList();
      })
      .catch((e) => {
        console.log(e);
      });
  };

  const searchTitleHandler = () => {
    TutorialDataService.findByTitle(searchTitle)
      .then((response) => {
        setTutorials(response.data);
        console.log(response.data);
      })
      .catch((e) => {
        console.log(e);
      });
  };

  return (
    <div className="tutorials-list-container">
      <div className="search-section">
        <div className="input-group mb-3">
          <input
            type="text"
            className="form-control"
            placeholder="Buscar por título"
            value={searchTitle}
            onChange={onChangeSearchTitle}
          />
          <div className="input-group-append">
            <button
              className="btn btn-secondary"
              type="button"
              onClick={searchTitleHandler}
            >
              <i className="fas fa-search"></i> Buscar
            </button>
          </div>
        </div>
      </div>

      <h1 className="titulo">Lista de Tutoriales</h1>

      <div className="row">
        {tutorials.length > 0 ? (
          tutorials.map((tutorial, index) => (
            <div
              className="col-md-4 mb-3"
              key={index}
              onClick={() => setCurrentTutorial(tutorial)}
            >
              <div
                className={`card ${
                  currentTutorial === tutorial ? "border-primary" : ""
                }`}
                style={{ cursor: "pointer" }}
              >
                <div className="card-body">
                  <div className="row">
                    {/* Imagen del tutorial */}
                    <div className="col-md-4">
                      {tutorial.imagen && (
                        <img
                          src={tutorial.imagen}
                          alt={tutorial.title}
                          className="tutorial-image"
                        />
                      )}
                    </div>
                    {/* Detalles del tutorial */}
                    <div className="col-md-8">
                      <h4 className="card-title">{tutorial.title}</h4>
                      <p className="card-text">
                        <b>Estado:</b>{" "}
                        {tutorial.published ? "Publicado" : "Pendiente"}
                      </p>
                    </div>
                  </div>
                </div>
                {/* Botones de editar y eliminar */}
                <div className="botones">
                  <Link to={"/tutorials/edit/" + tutorial.id}>
                    <button className="btn btn-warning">Editar</button>
                  </Link>
                  <button
                    className="btn btn-danger"
                    onClick={() => removeAllTutorials(tutorial.id)}
                  >
                    Eliminar
                  </button>
                </div>
              </div>
            </div>
          ))
        ) : (
          <div className="col-12">
            <div className="alert alert-warning" role="alert">
              No hay tutoriales disponibles...
            </div>
          </div>
        )}
      </div>

      {/* Botón flotante para agregar nuevos tutoriales */}
      <Link to={"/addTutorials"}>
        <button className="btn btn-primary boton-flotante">
          <i className="fas fa-plus"></i>
        </button>
      </Link>
    </div>
  );
};

export default TutorialsList;