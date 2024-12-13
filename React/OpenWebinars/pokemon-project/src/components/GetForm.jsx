import { useState } from "react"

function GetForm(props) {
    const[from, setForm] = useState(1);
    const[to, setTo] = useState(10);

    const hadleFromInput = (e) => {
        setForm(e.target.value);
    }

    const hadleTomInput = (e) => {
        setTo(e.target.value);
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        props.getPokemons(from, to);
    }

  return (
    <form onSubmit={handleSubmit}>
        <fieldset>
            <label htmlFor="from-pokemon">Form: </label>
            <input type="number" id="from-pokemon" min={1} onChange={hadleFromInput}></input>
        </fieldset>
        <fieldset>
            <label htmlFor="to-pokemon">To: </label>
            <input type="number" id="to-pokemon" onChange={hadleTomInput}></input>
        </fieldset>
        <button>Get Pokemon!</button>
    </form>
  )
}

export default GetForm