import React, { Component } from "react";
import TutorialDataService from "../services/tutorial.service";
import { Link } from "react-router-dom";

export default class Tutorial {
//   constructor(props) {
//     super(props);
//     this.onChangeSearchTitle = this.onChangeSearchTitle.bind(this);
//     this.retrieveTutorials = this.retrieveTutorials.bind(this);
//     this.refreshList = this.refreshList.bind(this);
//     this.setActiveTutorial = this.setActiveTutorial.bind(this);
//     this.removeAllTutorials = this.removeAllTutorials.bind(this);
//     this.searchTitle = this.searchTitle.bind(this);
//     //Hacemos el bind de los métodos porque al usar estos métodos en gestores de eventos los componentes basados
//     //en clases pierden el ámbito.
//     this.state = {
//       tutorials: [], //lista de tutoriales
//       currentTutorial: null, //tutorial seleccionado de la lista
//       currentIndex: -1,
//       searchTitle: "",
//     };
//   }

//   createTutorial() {
//     TutorialDataService.create(this.state.currentTutorial)
//       .then(() => {
//         this.props.history.push("/tutorials");
//       })
//       .catch((e) => {
//         console.log(e);
//       });
//   }

  render() {
    // const { searchTitle, tutorials, currentTutorial, currentIndex } = this.state;
    //ponemos los distintos elementos del estado en variables para simplificar su acceso dentro del método
    return (
      <div className="list row">
        add tutorial
      </div>
    );
  }
}
