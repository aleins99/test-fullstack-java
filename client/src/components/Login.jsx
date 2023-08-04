import { useState } from "react";
import PropTypes from "prop-types";
import axios from "axios";
import jwtDecode from "jwt-decode";
import axiosInstance from "../utils/axiosInstance";
const Login = ({ onLogin }) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, onError] = useState("");
  const token = btoa(`${"mike@gmail.com"}:${"mikepr21"}`);

  // puedes llamar a doLogin con los valores del nombre de usuario y contraseña
  const onLoginHandle = (e) => {
    e.preventDefault();
    const data = {
      email: email,
      password: password,
    };
    console.log(data);
    axios
      .post(
        "http://localhost:8080/api/authenticate",
        { email, password },
        { withCredentials: true }
      )
      .then((res) => {
        console.log(res.data.accessToken);
        onLogin(jwtDecode(res.data.accessToken).sub);
        window.localStorage.setItem("token", res.data.accessToken);
      })
      .catch((err) => {
        console.log(err);
        onError("Error al iniciar sesión");
      });
  };

  return (
    <div
      className="login dark:bg-slate-800 dark:text-white"
      onSubmit={onLoginHandle}
    >
      <form>
        <h2>Java Test</h2>
        <p>Por favor inicia sesión</p>
        <input
          aria-label="Username"
          placeholder="Username"
          id="email"
          type="text"
          onChange={(e) => {
            console.log(e.target.value);
            setEmail(e.target.value);
          }}
        />
        <input
          aria-label="Password"
          placeholder="Password"
          id="password"
          type="password"
          onChange={(e) => {
            setPassword(e.target.value);
          }}
        />
        {error && <p className="text-red-600">{error}</p>}
        <button type="submit" className="color-red">
          Login
        </button>
      </form>
    </div>
  );
};
Login.propTypes = {
  onLogin: PropTypes.func.isRequired,
};
export default Login;
