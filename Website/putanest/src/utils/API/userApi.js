import axios from 'axios';
import { URL } from './config';

const BASE_URL = `${URL}/api/users`;

// const BASE_URL = 'http://localhost:8080/api/users';
// const BASE_URL = 'https://putanest-h9ou.onrender.com/api/users';

export const getAllUsers = async () => {
    const response = await axios.get(BASE_URL);
    return response.data;
};

export const createUser = async (formData) => {
    const response = await axios.post(BASE_URL, formData, {
        headers: { 'Content-Type': 'multipart/form-data' }
    });
    return response.data;
};

export const login = async (username, password) => {
    const response = await axios.post(`${BASE_URL}/login`, { username, password });
    return response.data;
};
