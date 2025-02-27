import axios from 'axios';

// Cấu hình cơ bản cho Axios
const guestOrderApi = axios.create({
  // baseURL: 'http://localhost:8080/api/guest-orders',
  baseURL: 'https://putanest-h9ou.onrender.com/api/guest-orders',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Tạo đơn hàng cho khách
export const createGuestOrder = async (guestOrderDTO) => {
  try {
    const response = await guestOrderApi.post('/', guestOrderDTO);
    return response.data;
  } catch (error) {
    console.error('Error creating guest order:', error);
    throw error;
  }
};

// Lấy đơn hàng theo ID
export const getGuestOrderById = async (id) => {
  try {
    const response = await guestOrderApi.get(`/${id}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching guest order by ID:', error);
    throw error;
  }
};

// Lấy đơn hàng theo mã đơn hàng
export const getGuestOrderByOrderCode = async (orderCode) => {
  try {
    const response = await guestOrderApi.get(`/order-code/${orderCode}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching guest order by orderCode:', error);
    throw error;
  }
};

// Lấy tất cả đơn hàng
export const getAllGuestOrders = async () => {
  try {
    const response = await guestOrderApi.get('/');
    return response.data;
  } catch (error) {
    console.error('Error fetching all guest orders:', error);
    throw error;
  }
};

// Xóa đơn hàng theo ID
export const deleteGuestOrder = async (id) => {
  try {
    await guestOrderApi.delete(`/${id}`);
  } catch (error) {
    console.error('Error deleting guest order:', error);
    throw error;
  }
};

export default guestOrderApi;
