import axios from "axios";

const agendaService = axios.create({
  baseURL: "http://agenda2025.us-east-1.elasticbeanstalk.com:8099/api/v1",
  headers: {
    "Content-type": "application/json",
  },
});

export default agendaService;
