import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/products';
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
    const response = await axios.post(BASE_URL, formData, {
        headers: { 'Content-Type': 'multipart/form-data' }
    });
    return response.data;
};

// Cập nhật sản phẩm
export const updateProduct = async (id, formData) => {
    const response = await axios.put(`${BASE_URL}/${id}`, formData, {
        headers: { 'Content-Type': 'multipart/form-data' }
    });
    return response.data;
};

// Xóa sản phẩm
export const deleteProduct = async (id) => {
    await axios.delete(`${BASE_URL}/${id}`);
};

export const getProductsByCategory = async (categoryId) => {
    const response = await axios.get(
        `https://putanest-h9ou.onrender.com/api/products/category/${categoryId}`
    );
    return response.data;
};