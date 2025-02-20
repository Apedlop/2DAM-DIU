import axios from "axios";

const productoService = axios.create({
  baseURL: "http://localhost:8081/api/v1",
  headers: {
    "Content-type": "application/json",
  },
});

export default productoService;
