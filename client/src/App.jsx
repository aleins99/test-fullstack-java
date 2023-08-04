import { useState, useEffect } from "react";
import "./App.css";
import Login from "./components/Login";
import Home from "./components/Home";
import jwtDecode from "jwt-decode";
import axios from "axios";
function App() {
  const [userEmail, setUserEmail] = useState(() => {
    const ls = localStorage.getItem("token");
    const localData = ls ? jwtDecode(ls).sub : null;
    return localData ? localData : null;
  });
  const onLoginHandler = (email) => {
    console.log(email);
    setUserEmail(email);
  };
  return (
    <>{userEmail == null ? <Login onLogin={onLoginHandler} /> : <Home />}</>
  );
}

export default App;
