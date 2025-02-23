import React, { useState } from "react";
import tutorialService from "../service/tutorials.service";
import { useNavigate } from "react-router-dom";
import "./style/TutorialsAdd.css"; // Archivo CSS para estilos personalizados

const TutorialsAdd = () => {
  const [newTutorial, setNewTutorial] = useState({
    title: "",
    description: "",
    published: false,
    imagen: "", // Nuevo campo para la URL de la imagen
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
    tutorialService
      .create(newTutorial)
      .then(() => {
        navegar("/tutorials");
      })
      .catch((e) => {
        console.log(e);
      });
  };

  return (
    <div className="tutorials-add-container">
      <div className="tutorials-add-card">
        <h2 className="tutorials-add-title">Add New Tutorial</h2>
        <form>
          <div className="form-group">
            <label htmlFor="title" className="form-label">
              Title
            </label>
            <input
              type="text"
              className="form-control"
              id="title"
              required
              value={newTutorial.title}
              onChange={handleInputChange}
              placeholder="Enter tutorial title"
            />
          </div>
          <div className="form-group">
            <label htmlFor="description" className="form-label">
              Description
            </label>
            <input
              type="text"
              className="form-control"
              id="description"
              required
              value={newTutorial.description}
              onChange={handleInputChange}
              placeholder="Enter tutorial description"
            />
          </div>
          <div className="form-group">
            <label htmlFor="imagen" className="form-label">
              Image URL
            </label>
            <input
              type="url"
              className="form-control"
              id="imagen"
              value={newTutorial.imagen}
              onChange={handleInputChange}
              placeholder="Paste the image URL here"
            />
          </div>
          <div className="form-group">
            <label className="form-label">Published:</label>
            <div className="radio-group">
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
          <button
            type="button"
            onClick={createTutorial}
            className="btn-submit"
          >
            Submit
          </button>
        </form>
      </div>
    </div>
  );
};

export default TutorialsAdd;