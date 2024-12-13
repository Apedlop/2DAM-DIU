import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { PokemonProviderWrapper } from './context/pokemon.context.jsx'
import { BrowserRouter } from 'react-router'

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <BrowserRouter>
      <PokemonProviderWrapper>
        <App />
      </PokemonProviderWrapper>
    </BrowserRouter>
  </StrictMode>
);
