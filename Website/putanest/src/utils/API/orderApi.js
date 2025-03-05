import axios from "axios";

const BASE_URL = "http://localhost:8080/api/orders";
// const BASE_URL = 'https://putanest-h9ou.onrender.com/api/orders';
// Tạo đơn hàng mới
export const createOrder = async (orderRequest) => {
  const response = await axios.post(BASE_URL, orderRequest, {
    headers: { "Content-Type": "application/json" },
  });
  return response.data;
};

// Lấy đơn hàng theo userId
export const getOrdersByUserId = async (userId) => {
  const response = await axios.get(`${BASE_URL}/user/${userId}`);
  return response.data;
};

// Lấy tất cả đơn hàng
export const getAllOrders = async () => {
  const response = await axios.get(BASE_URL);
  return response.data;
};

// Lấy đơn hàng theo userId phân trang
export const getAllOrdersPaginationByUserId = async (
  page = 0,
  size = 2,
  userId
) => {
  const response = await axios.get(`${BASE_URL}/paginated-by-user/${userId}`, {
    params: { page, size },
  });
  return response.data;
};

// Lấy tất cả đơn hàng với tham số mặc định
export const getAllOrdersPagination = async (page = 0, size = 2) => {
  const response = await axios.get(`${BASE_URL}/paginated`, {
    params: { page, size },
  });
  return response.data;
};

export const updateOrderStatus = async (orderId, status) => {
  try {
    const response = await axios.put(`${BASE_URL}/${orderId}/status`, status, {
      headers: {
        "Content-Type": "application/json",
      },
    });
    return response.data;
  } catch (error) {
    throw error.response?.data || "Lỗi khi cập nhật trạng thái đơn hàng";
  }
};
