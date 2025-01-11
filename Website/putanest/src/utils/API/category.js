import axios from "axios";

const BASE_URL = 'http://localhost:8080/api/categories';

export const getAllCategories = async () => {
    const response = await axios.get(BASE_URL);
    return response.data;
};

export const getCategoryById = async (id) => {
    const response = await axios.get(`${BASE_URL}/${id}`);
    return response.data;
};

export const createCategory = async (categoryName, description) => {
    const response = await axios.post(BASE_URL, {
        categoryName,
        description,
    });
    return response.data;
};

export const updateCategory = async (id, categoryName, description) => {
    const response = await axios.put(`${BASE_URL}/${id}`, {
        categoryName,
        description,
    });
    return response.data;
};

export const deleteCategory = async (id) => {
    const response = await axios.delete(`${BASE_URL}/${id}`);
    return response.data;
};
