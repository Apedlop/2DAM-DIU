import "./HomePage.css"
import logo from "../assets/pokemon-logo.png"
import { Link } from "react-router"

function HomePage() {
  return (
    <section id="home-page">
        <h1 className="title">Bienvenido</h1>
        <img src={logo} alt="Pokemon Logo" className="logo"></img>
        <Link to="/pokemons" className="link">Entrar</Link>
    </section>
  )
}

export default HomePage