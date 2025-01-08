import { Component, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import Contador from './components/Contador'

/* Usando Hooks */
// function App() {
//   const [num, setNum] = useState(0);

//   const incrementar = () => setNum(num + 1);
//   const decrementar = () => setNum(num - 1);
//   const resetear = () => setNum(0);

//   return (
//     <>
//       <Contador
//         num={num}
//         incrementar={incrementar}
//         decrementar={decrementar}
//         resetear={resetear}
//         clase="incButton"
//       ></Contador>
//     </>
//   );
// }

/* Usando comonentes basados en clases */
class App extends Component {
  constructor() {
    super();
    this.state = {
      num: 0,
    };
  }

  // Métodos para manejar el estado
  incrementar = () => {
    this.setState({ num: this.state.num + 1 });
  };

  decrementar = () => {
    this.setState({ num: this.state.num - 1 });
  };

  resetear = () => {
    this.setState({ num: 0 });
  };

  render() {
    return (
      <Contador
        num={this.state.num}
        incrementar={this.incrementar}
        decrementar={this.decrementar}
        resetear={this.resetear}
        clase="incButton"
      ></Contador>
    );
  }
}

export default App
