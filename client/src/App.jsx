import { useState, useEffect } from "react";
import "./App.css";
import Login from "./components/Login";
function App() {
    const [userId, setUserId] = useState(null);
    const onLoginHandler = (userId) => {
        console.log(userId);
        setUserId(userId);
    };
    const onLogoutHandler = () => {
        setUserId(null);
        localStorage.clear();
    };

    return (
        <>
                <Login onLogin={onLoginHandler} />

        </>
    );
}

export default App;
