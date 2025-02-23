import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/order-details';
// const BASE_URL = 'https://putanest-h9ou.onrender.com/api/order-details';

// Hàm gọi API để lấy chi tiết đơn hàng theo orderId
export const getOrderDetailsByOrderId = async (orderId) => {
  try {
    const response = await axios.get(`${BASE_URL}/${orderId}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching order details:', error);
    throw error;
  }
};