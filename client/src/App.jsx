import { useState, useEffect } from "react";
import "./App.css";
import Login from "./components/Login";
import Home from "./components/Home";
import jwtDecode from "jwt-decode";
import axios from "axios";
function App() {
  return (
    <div className="App">
      <Home />
    </div>
  );
}

export default App;
