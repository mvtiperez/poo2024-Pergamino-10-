import api from "./api";

export const createArtistUser = async (userData) => {
  const response = await api.post("/artist", userData);
  return response.data;
};
