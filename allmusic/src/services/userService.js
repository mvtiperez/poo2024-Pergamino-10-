import api from "./api";

export const getUserByUsername = async (username) => {
  const response = await api.get(`/${username}`);
  return response.data;
};

export const deleteUserByUsername = async (username) => {
  const response = await api.delete(`/${username}`);
  return response.data;
};
