import React, { Component } from "react";
import TutorialDataService from "../services/tutorial.service";
import { Link } from "react-router-dom";

export default class AddTutorial extends Component {
  constructor(props) {
    super(props);
    //   this.onChangeSearchTitle = this.onChangeSearchTitle.bind(this);

    //Hacemos el bind de los métodos porque al usar estos métodos en gestores de eventos los componentes basados
    //en clases pierden el ámbito.
    //   this.state = {
    //     tutorials: [], //lista de tutoriales
    //     currentTutorial: null, //tutorial seleccionado de la lista
    //     currentIndex: -1,
    //     searchTitle: "",
    //   };
    this.state = {
      currentTutorial: {
        title: "",
        description: "",
        published: false,
      },
      message: "",
    };
  }

  createTutorial() {
    TutorialDataService.create(this.state.currentTutorial)
      .then(() => {
        this.props.history.push("/tutorials");
      })
      .catch((e) => {
        console.log(e);
      });
  }

  render() {
    // const { searchTitle, tutorials, currentTutorial, currentIndex } = this.state;
    //ponemos los distintos elementos del estado en variables para simplificar su acceso dentro del método
    return (
      <div className="container">
        <form>
          <div className="form-group">
            <label htmlFor="title">Title</label>
            <input type="text" className="form-control" id="title" required />
          </div>
          <div className="form-group">
            <label htmlFor="description">Description</label>
            <input
              type="text"
              className="form-control"
              id="description"
              required
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
                />
                <label className="form-check-label" htmlFor="radioButtonNo">
                  No
                </label>
              </div>
            </div>
          </div>
          <button onClick={this.createTutorial} className="btn btn-success">
            Submit
          </button>
        </form>
      </div>
    );
  }
}
