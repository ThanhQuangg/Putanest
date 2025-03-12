import axios from "axios";
import { URL } from "./config";

const BASE_URL = `${URL}/api/categories`;

// const BASE_URL = 'http://localhost:8080/api/categories';
// const BASE_URL = 'https://putanest-h9ou.onrender.com/api/categories';

export const getAllCategories = async () => {
  const response = await axios.get(BASE_URL);
  return response.data;
};

export const getCategoryById = async (id) => {
  const response = await axios.get(`${BASE_URL}/${id}`);
  return response.data;
};

export const createCategory = async (categoryName, description) => {
  const token = localStorage.getItem("token");
  const config = {
    headers: {
      ...(token && { Authorization: `Bearer ${token}` }),
    },
  };
  const response = await axios.post(
    BASE_URL,
    {
      categoryName,
      description,
    },
    config
  );
  return response.data;
};

export const updateCategory = async (id, categoryName, description) => {
  const token = localStorage.getItem("token");
  const config = {
    headers: {
      ...(token && { Authorization: `Bearer ${token}` }),
    },
  };
  const response = await axios.put(
    `${BASE_URL}/${id}`,
    {
      categoryName,
      description,
    },
    config
  );
  return response.data;
};

export const deleteCategory = async (id) => {
  const response = await axios.delete(`${BASE_URL}/${id}`);
  return response.data;
};
