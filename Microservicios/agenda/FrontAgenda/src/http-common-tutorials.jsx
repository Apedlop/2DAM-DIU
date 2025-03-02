import axios from "axios";

// TUTORIALS
export default axios.create({
  baseURL: "http://agenda2025.us-east-1.elasticbeanstalk.com:8098/api/v1",
  headers: {
    "Content-type": "application/json",
  },
});
