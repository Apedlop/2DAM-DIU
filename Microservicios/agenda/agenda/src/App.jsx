import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import { Link, Route, Switch } from 'react-router-dom'
import AgendaList from './components/AgendaList'

function App() {

  return (
    <div>
      <nav className="navbar">
        <Link to={"/agenda"} className="navbar-brand">
          Agenda
        </Link>
        <div className="navbar-nav mr-auto">
          <li className="nav-item">
            <Link to={"/agenda"} className="nav-link">
              Agenda
            </Link>
          </li>
          <li className="nav-item">
            <Link to={"/add"} className="nav-link">
              Añadir
            </Link>
          </li>
          </div>
      </nav>
      <Switch>
        <Route exact path={["/", "/agenda"]} component={AgendaList}/>
      </Switch>
    </div>
  )
}

export default App
