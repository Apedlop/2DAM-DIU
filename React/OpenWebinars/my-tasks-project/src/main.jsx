import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import "./index.css";
import { BrowserRouter } from "react-router-dom";
import { TaskProviderWrapper } from "./context/task.context.jsx";

ReactDOM.createRoot(document.getElementById("root")).render(
  // <React.StrictMode>
    <BrowserRouter>
      <TaskProviderWrapper>
        <App />
      </TaskProviderWrapper>
    </BrowserRouter>
  // </React.StrictMode>
);