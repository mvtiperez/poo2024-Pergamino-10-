import api from "./api";

export const createEnthusiastUser = async (userData) => {
  const response = await api.post("/enthusiast", userData);
  return response.data;
};
