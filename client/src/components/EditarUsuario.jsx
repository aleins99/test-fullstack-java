import { useEffect, useState } from "react";
import axiosInstance from "../utils/axiosInstance";
import { useNavigate, useParams } from "react-router-dom";

export default function ProductoForm() {
  const params = useParams();

  const [isChecked, setIsChecked] = useState(false);
  const navigate = useNavigate();
  const [error, onError] = useState("");
  const [usuario, setUsuario] = useState();

  console.log(params.id);
  useEffect(() => {
    const getUsuario = async () => {
      const response = await axiosInstance.get(`usuarios/${params.id}`);
      setUsuario(response.data[0]);
      setIsChecked(response.data[0].estado === "ACTIVO" ? true : false);
    };
    getUsuario();
  }, []);

  const handleCheckboxChange = (e) => {
    setIsChecked(e.target.checked);
  };
  const onSubmit = async (e) => {
    e.preventDefault();

    const form = e.target;
    const formData = new FormData(form);
    const formJson = Object.fromEntries(formData.entries());
    formJson.estado = isChecked === true ? "ACTIVO" : "INACTIVO";

    try {
      const response = await axiosInstance.patch(
        `usuarios/${params.id}`,
        formJson
      );
      console.log("result: ", response);
      if (response.status === 200) {
        navigate("/");
      }
    } catch (error) {
      console.error(error);

      if (error.response) {
        onError(error.response.data);
      }
    }
  };

  return (
    <div className="w-full grid place-items-center  dark:bg-slate-800 dark:text-white h-full ">
      {usuario && (
        <form
          onSubmit={onSubmit}
          className="bg-white shadow-md rounded px-10 pt-6 pb-8 mb-4 w-2/6  dark:bg-slate-800 dark:text-white"
        >
          <h2 className="bold">Editar Usuario</h2>
          <div className="mb-4 w-80px w-full">
            <label
              className="block text-gray-700 dark:text-white text-sm font-bold mb-2"
              htmlFor="rol"
            >
              Rol
            </label>
            <select
              name="rol"
              id="rol"
              required
              className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            >
              <option value="ADMIN">Admin</option>
              <option selected value="CONSULTOR">
                Consultor
              </option>
            </select>
            <div className="mb-4 w-80px w-full">
              <p className="my-2">Estado</p>
              <label className="block text-gray-700 dark:text-white text-sm font-bold mb-2 flex items-center">
                <input
                  type="checkbox"
                  className="hidden"
                  id="checkboxInput"
                  value={isChecked}
                  onChange={handleCheckboxChange}
                />
                <span
                  className={`w-5 h-5 border-2 rounded ${
                    isChecked
                      ? "bg-blue-500 border-blue-500"
                      : "bg-white border-gray-400"
                  } mr-2`}
                ></span>
                ACTIVO
              </label>
              {error && <p className="text-red-600">{error}</p>}
            </div>
          </div>
          <div className="flex items-center justify-between">
            <button className="bg-slate-800 hover:bg-slate-700 dark:bg-blue-500 dark:hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
              Editar Usuario
            </button>
          </div>
        </form>
      )}{" "}
    </div>
  );
}
