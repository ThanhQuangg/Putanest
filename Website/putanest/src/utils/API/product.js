import axios from "axios";
import { URL } from "./config";

const BASE_URL = `${URL}/api/products`;

// const BASE_URL = 'http://localhost:8080/api/products';
// const BASE_URL = 'https://putanest-h9ou.onrender.com/api/products';

// Lấy tất cả sản phẩm
export const getAllProducts = async () => {
  const response = await axios.get(BASE_URL);
  return response.data;
};

// Lấy sản phẩm theo ID
export const getProductById = async (id) => {
  const response = await axios.get(`${BASE_URL}/${id}`);
  return response.data;
};

// Tạo sản phẩm mới
export const createProduct = async (formData) => {
  const token = localStorage.getItem("token");
  const config = {
    headers: {
      "Content-Type": "multipart/form-data",
      ...(token && { Authorization: `Bearer ${token}` }),
    },
  };
  const response = await axios.post(BASE_URL, formData, config);
  return response.data;
};

// Cập nhật sản phẩm
export const updateProduct = async (id, formData) => {
  const token = localStorage.getItem("token");
  const config = {
    headers: {
      "Content-Type": "multipart/form-data",
      ...(token && { Authorization: `Bearer ${token}` }),
    },
  };
  const response = await axios.put(`${BASE_URL}/${id}`, formData, config);
  return response.data;
};

// Xóa sản phẩm
export const deleteProduct = async (id) => {
  const token = localStorage.getItem("token");
  const config = {
    headers: {
      ...(token && { Authorization: `Bearer ${token}` }),
    },
  };
  await axios.delete(`${BASE_URL}/${id}`, config);
};
export const getProductsByCategory = async (categoryId) => {
  const response = await axios.get(
    `https://putanest-h9ou.onrender.com/api/products/category/${categoryId}`
  );
  return response.data;
};
