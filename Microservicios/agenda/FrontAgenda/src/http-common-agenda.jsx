import axios from "axios";

// AGENDA
export default axios.create({
  baseURL: "http://localhost:8099/api/v1",
  headers: {
    "Content-type": "application/json",
  },
});

// TUTORIALS