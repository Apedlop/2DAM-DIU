import { Component } from "react";
import "../style/counterApp.css";

/* Usando Hooks */
// function Contador(props) {
//   const { num, incrementar, decrementar, resetear, clase } = props;

//   return (
//     <div className="container-counter">
//       <div className="counter">
//         <h1>{num}</h1>
//       </div>
//       <div className="buttons">
//         <button className={clase} onClick={incrementar}>
//           Incrementar
//         </button>
//         <button onClick={decrementar}>Decrementar</button>
//         <button onClick={resetear}>Resetear</button>
//       </div>
//     </div>
//   );
// }
// export default Contador;

/* Usando componentes basados en clases */
export default class Contador extends Component {
  render() {
    return (
      <div className="container-counter">
        <div className="counter">
          <h1>{this.props.num}</h1>
        </div>
        <div className="buttons">
          <button className={this.props.clase} onClick={this.props.incrementar}>
            Incrementar
          </button>
          <button onClick={this.props.decrementar}>Decrementar</button>
          <button onClick={this.props.resetear}>Resetear</button>
        </div>
      </div>
    );
  }
}


