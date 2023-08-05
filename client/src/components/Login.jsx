import { useState } from "react";
import PropTypes from "prop-types";
import axios from "axios";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, onError] = useState("");

  const onLoginHandle = async (e) => {
    e.preventDefault();
    const data = {
      email: email,
      password: password,
    };

    try {
      const result = await axios.post(
        "http://localhost:8080/api/authenticate",
        data
      );
      console.log("result: ", result);

      if (result.status === 200) {
        localStorage.setItem("token", result.data.accessToken);
        window.location.reload();
      } else {
        console.log("rdadasd");
      }
    } catch (error) {
      console.error(error);

      if (error.response) {
        console.error("Response data: ", error.response.data);
        onError(error.response.data);
        console.error("Response status: ", error.response.status);
        console.error("Response headers: ", error.response.headers);
      }
    }
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
