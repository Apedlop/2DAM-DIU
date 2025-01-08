import{R as j,j as e,r as i,P as u,L as x,U as p,N as P}from"./index-rxqo8U_r.js";function f(n){const{pokemon:s,selectPokemon:t,selectPokemon2:o}=n;return s.id?e.jsxs("li",{className:"pokemon-card",onClick:()=>t(s),onAuxClick:()=>o(s),children:[e.jsx("h2",{className:"pokemon-name",children:s.name}),e.jsx("img",{src:s.sprites.front_default,alt:"pokemon img",className:"pokemon-imgf"}),e.jsxs("h3",{className:"text",children:["HP: ",s.stats[0].base_stat]})]}):e.jsx("p",{className:"loading",children:"Loading..."})}const g=j.memo(f);function N(n){const[s,t]=i.useState(1),[o,m]=i.useState(10),l=r=>{t(r.target.value)},c=r=>{m(r.target.value)},a=r=>{r.preventDefault(),n.getPokemons(s,o)};return e.jsxs("form",{onSubmit:a,children:[e.jsxs("fieldset",{children:[e.jsx("label",{htmlFor:"from-pokemon",children:"Form: "}),e.jsx("input",{type:"number",id:"from-pokemon",min:1,onChange:l})]}),e.jsxs("fieldset",{children:[e.jsx("label",{htmlFor:"to-pokemon",children:"To: "}),e.jsx("input",{type:"number",id:"to-pokemon",onChange:c})]}),e.jsx("button",{children:"Get Pokemon!"})]})}function C(n){const{pokemons:s,setPokemons:t,fetchPokemon:o}=i.useContext(u);i.useEffect(()=>{m(1,10)},[]);const m=async(c,a)=>{const r=[];for(let d=c;d<=a;d++){const h=await o(d);r.push(h)}t(r)},l=s.map(c=>e.jsx(g,{pokemon:c,selectPokemon:n.selectPokemon,selectPokemon2:n.selectPokemon2},c.id));return e.jsxs("div",{children:[e.jsx(N,{getPokemons:m}),e.jsx("ul",{className:"pokemon-list",children:l})]})}function k(n){const[s,t]=i.useState(0),o=()=>{t(s+1)};return e.jsx(e.Fragment,{children:n.render(s,o)})}function L(n){const{pokemon:s,likes:t,increaseLikes:o}=n;return e.jsxs("section",{className:"selected-pokemon",children:[e.jsxs("div",{children:[e.jsx("h3",{children:"Pokemon 1"}),e.jsxs("h3",{children:["Likes ",t,e.jsx("button",{onClick:o,children:"+"})]}),e.jsx(x,{to:`/pokemons/${s.id}`,children:"Ver detalles"})]}),e.jsxs("div",{className:"pokemon-container",children:[e.jsx("h2",{className:"text",children:s.name}),e.jsx("img",{src:s.sprites.front_default,alt:"pokemon img",className:"pokemon-img"}),e.jsxs("h3",{className:"text",children:["HP: ",s.stats[0].base_stat]})]})]})}function b(n){const{pokemon:s,likes:t,increaseLikes:o}=n;return e.jsxs("section",{className:"selected-pokemon",children:[e.jsxs("div",{children:[e.jsx("h3",{children:"Pokemon 2"}),e.jsxs("h3",{children:["Likes ",t,e.jsx("button",{onClick:o,children:"+"})]}),e.jsx(x,{to:`/pokemons/${s.id}`,children:"Ver detalles"})]}),e.jsxs("div",{className:"pokemon-container pokemon2",children:[e.jsx("h2",{className:"text",children:s.name}),e.jsx("img",{src:s.sprites.front_default,alt:"pokemon img",className:"pokemon-img"}),e.jsxs("h3",{className:"text",children:["HP: ",s.stats[0].base_stat]})]})]})}function v(){const{user:n}=i.useContext(p);if(!n.isLoggedIn)return e.jsx(P,{to:"/error"});const[s,t]=i.useState(),[o,m]=i.useState(),l=(a,r)=>e.jsx(L,{pokemon:s,likes:a,increaseLikes:r}),c=(a,r)=>e.jsx(b,{pokemon:o,likes:a,increaseLikes:r});return e.jsxs(e.Fragment,{children:[e.jsx("h2",{children:"Pokemon Seleccionado"}),s&&e.jsx(k,{render:l}),o&&e.jsx(k,{render:c}),e.jsx("h2",{children:"Lista de Pokemons"}),e.jsx(C,{selectPokemon:t,selectPokemon2:m})]})}export{v as default};
