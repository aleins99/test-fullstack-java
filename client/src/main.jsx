import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import "./index.css";
import Login from "./components/Login";
import Home from "./components/Home";
import UsuarioForm from "./components/UsuarioForm";
import EditarUsuario from "./components/EditarUsuario";

import { createBrowserRouter, RouterProvider } from "react-router-dom";
import jwtDecode from "jwt-decode";
const ls = localStorage.getItem("token");
const localData = ls ? jwtDecode(ls).sub : null;

const router = createBrowserRouter([
  {
    path: "/",
    element: <>{localData == null ? <Login /> : <Home />}</>,
  },
  {
    path: "/usuario/agregar",
    element: <UsuarioForm />,
  },
  {
    path: "/usuario/editar/:id",
    element: <EditarUsuario />,
  },
]);
ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
