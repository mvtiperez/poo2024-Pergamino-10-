import api from "./api";

export const getPlaylistById = async (id) => {
  const response = await api.get(`/playlist/${id}`);
  return response.data;
};
