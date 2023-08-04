import { useState, useEffect } from "react";
import userIcon from "../assets/user.svg";
import axiosInstance from "../utils/axiosInstance";
import editBtn from "../assets/edit-btn.svg";
import "../App.css";
import axios from "axios";
const Usuarios = () => {
  const [usuarios, setUsuarios] = useState([]);

  useEffect(() => {
    let getUsuarios = async () => {
      console.log(localStorage.getItem("email"));
      let response = await axiosInstance.get("usuarios");
      if (response.status === 200) {
        setUsuarios(response.data);
        console.log(response.data);
      }
    };
    getUsuarios();
    console.log(usuarios[0]);
  }, []);
  async function eliminarUsuario(id) {
    const response = await axiosInstance.delete(`usuarios/${id}`);
    console.log(response);
    if (response.status === 200) {
      setUsuarios(usuarios.filter((usuario) => usuario.id !== id));
    } else {
      console.log(response.data);
      console.error("Error al eliminar");
    }
  }
  const logoutHandle = () => {
    localStorage.clear();
    window.location.reload();
  };
  return (
    <div className="px-5 py-5 dark:bg-slate-800 h-full">
      <div className="flex justify-between">
        <h1 className="text-center sm:text-left text-4xl dark:text-white">
          Lista de Usuarios
        </h1>
        <button
          className="bg-white color-black hover:color-white"
          onClick={logoutHandle}
        >
          Log out
        </button>
      </div>
      <ul className="grid lg:grid-cols-4 gap-4 grid-cols-2 md:grid-cols-3 place-content-center">
        {usuarios.map((usuario) => {
          return (
            usuario.rol !== "ADMIN" && (
              <li
                key={usuario.id}
                className="max-w-sm rounded overflow-hidden shadow-lg px-4 py-8"
              >
                <div className="grid grid-cols-3">
                  <img
                    className="w-8 color-white col-span-2 mb-5"
                    src={userIcon}
                    alt={usuario.nombreCompleto}
                  />
                  {usuario.rol === "ADMIN" && (
                    <img
                      src={editBtn}
                      alt="Boton de editar producto"
                      className="w-6 h-6 hover:cursor-pointer mx-6"
                      onClick={() => {
                        navigate(`/usuario/${usuario.id}`);
                      }}
                    />
                  )}

                  <span className="dark:text-white text-gray-800 col-span-2">
                    {usuario.nombreCompleto} {usuario.last_name}
                  </span>
                </div>

                <p className="dark:text-white text-blue-800 text-base ">
                  Correo :{" "}
                  <span className="text-gray-800 dark:text-white">
                    {usuario.email}{" "}
                  </span>
                </p>
                <p className="dark:text-white text-blue-800 text-base">
                  Rol:{" "}
                  <span className="text-gray-800 dark:text-white">
                    {usuario.rol}
                  </span>
                </p>
                {usuario.rol === "ADMIN" && (
                  <div className="flex justify-end">
                    <button
                      onClick={() => eliminarUsuario(usuario.id)}
                      className="bg-slate-800 hover:bg-slate-700 dark:bg-blue-500 dark:hover:bg-blue-700 text-white flex-end dark:bg-blue-600"
                    >
                      Eliminar Usuario
                    </button>
                  </div>
                )}
              </li>
            )
          );
        })}
      </ul>
    </div>
  );
};

export default Usuarios;
