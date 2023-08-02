import { useState } from "react";
import PropTypes from "prop-types";
import axios from "axios";
const Login = ({ onLogin }) => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, onError] = useState("");
    const loginHandle = async (e) => {
        e.preventDefault();
        // login and get an user with JWT token

        await axios
            .post("http://localhost:8080/login/", {
                username,
                password,
            })
            .then(function (response) {
                console.log(response.data);
                window.localStorage.setItem("authToken", JSON.stringify(response.data));
                onLogin(response.data);
            })
            .catch(function (error) {
                if (error.response.status === 400) {
                    onError("Los campos no pueden estar en blanco");
                } else {
                    onError("Algo ha salido mal, Verifique sus credenciales");
                }
            });
    };

    return (
        <div className="login dark:bg-slate-800 dark:text-white">
            <form onSubmit={loginHandle}>
                <h2>Java Test</h2>
                <p>Por favor inicia sesión</p>
                <input
                    aria-label="Username"
                    placeholder="Username"
                    id="username"
                    type="text"
                    onChange={(e) => {
                        console.log(e.target.value);
                        setUsername(e.target.value);
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
                <button type="submit">Login</button>
            </form>
        </div>
    );
};
Login.propTypes = {
    onLogin: PropTypes.func.isRequired,
};
export default Login;
