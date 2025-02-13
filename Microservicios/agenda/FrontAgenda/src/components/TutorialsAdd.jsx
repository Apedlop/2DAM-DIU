import tutorialsService from "../service/tutorials.service";
import { useNavigate } from "react-router-dom";
import React, { useState } from "react";

function TutorialsAdd(props) {
  const [newTutorial, setNewTutorial] = useState({
    title: "",
    description: "",
    published: false,
  });

  const navegar = useNavigate();

  // Esta función actualiza el estado 'newTutorial' con el valor del campo de entrada que ha cambiado.
  const handleInputChange = (e) => {
    const { id, value } = e.target;
    setNewTutorial({
      ...newTutorial,
      [id]: value,
    });
  };

  const handlePublishedChange = (e) => {
    const { id } = e.target;
    setNewTutorial({
      ...newTutorial,
      published: id === "radioButtonYes",
    });
  };

  const createTutorial = () => {
    tutorialsService
      .create(newTutorial)
      .then(() => {
        navegar.push("/tutorials");
      })
      .catch((e) => {
        console.log(e);
      });
  };

  const crearContacto = () => {
    createTutorial;
    props;
  };

  const createCancel = () => {
    navegar.push("/agenda");
  };

  return (
    <div className="container">
      <form>
        <div className="form-group">
          <label htmlFor="title">Title</label>
          <input
            type="text"
            className="form-control"
            id="title"
            required
            value={newTutorial.title}
            onChange={handleInputChange}
          />
        </div>
        <div className="form-group">
          <label htmlFor="description">Description</label>
          <input
            type="text"
            className="form-control"
            id="description"
            required
            value={newTutorial.description}
            onChange={handleInputChange}
          />
        </div>
        <div className="form-group">
          <label>Published: </label>
          <div className="d-flex gap-5">
            <div className="form-check">
              <input
                className="form-check-input"
                type="radio"
                name="flexRadioDefault"
                id="radioButtonYes"
                checked={newTutorial.published === true}
                onChange={handlePublishedChange}
              />
              <label className="form-check-label" htmlFor="radioButtonYes">
                Yes
              </label>
            </div>
          </div>
          <div className="d-flex gap-5">
            <div className="form-check">
              <input
                className="form-check-input"
                type="radio"
                name="flexRadioDefault"
                id="radioButtonNo"
                checked={newTutorial.published === false}
                onChange={handlePublishedChange}
              />
              <label className="form-check-label" htmlFor="radioButtonNo">
                No
              </label>
            </div>
          </div>
        </div>
        <button onClick={crearContacto} className="btn btn-success">
          Añadir
        </button>
        <button onClick={createCancel} className="btn btn-danger">
          Cancelar
        </button>
      </form>
    </div>
  );
}

export default TutorialsAdd;
