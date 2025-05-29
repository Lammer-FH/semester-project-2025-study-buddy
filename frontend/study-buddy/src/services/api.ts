import axios from "axios";

const api = axios.create({
  baseURL: "/", // will be proxied to http://localhost:8080
  headers: {
    "Content-Type": "application/json",
  },
});

export default api;
