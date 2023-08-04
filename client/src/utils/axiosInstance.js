import axios from "axios";

const baseURL = "http://localhost:8080/api/";
const email = localStorage.getItem("email");
const password = localStorage.getItem("password");
const token = localStorage.getItem("token");
console.log(email);
const axiosInstance = await axios.create({
  baseURL,
  headers: {
    Authorization: `Bearer ${token}`,
  },
});

export default axiosInstance;
