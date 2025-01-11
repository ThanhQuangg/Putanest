import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/orders';

// Tạo đơn hàng mới
export const createOrder = async (orderRequest) => {
    const response = await axios.post(BASE_URL, orderRequest, {
        headers: { 'Content-Type': 'application/json' }
    });
    return response.data;
};

// Lấy đơn hàng theo userId
export const getOrdersByUserId = async (userId) => {
    const response = await axios.get(`${BASE_URL}/user/${userId}`);
    return response.data;
};