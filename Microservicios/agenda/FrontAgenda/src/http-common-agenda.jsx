import axios from "axios";

const agendaService = axios.create({
  baseURL: "http://localhost:8099/api/v1",
  headers: {
    "Content-type": "application/json",
  },
});

export default agendaService;
