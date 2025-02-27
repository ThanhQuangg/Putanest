import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import {
  createOrder,
  getOrdersByUserId,
  getAllOrders,
  updateOrderStatus,
} from "../../utils/API/orderApi";

// Thunk để tạo đơn hàng mới
export const createNewOrder = createAsyncThunk(
  "orders/create",
  async (orderRequest) => {
    const response = await createOrder(orderRequest);
    return response;
  }
);

// Thunk để lấy đơn hàng theo userId
export const fetchOrdersByUserId = createAsyncThunk(
  "orders/fetchByUserId",
  async (userId) => {
    const response = await getOrdersByUserId(userId);
    return response;
  }
);

export const fetchAllOrders = createAsyncThunk(
  "orders/fetchAllOrders",
  async () => {
    const response = await getAllOrders();
    return response;
  }
);

// Async thunk để cập nhật trạng thái đơn hàng
export const updateOrderStatusThunk = createAsyncThunk(
  'orders/updateStatus',
  async ({ orderId, status }, { rejectWithValue }) => {
    try {
      const response = await updateOrderStatus(orderId, status);
      return { orderId, status: response }; 
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);

const ordersSlice = createSlice({
  name: "orders",
  initialState: {
    orders: [],
    // order: null,
    status: "idle",
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(createNewOrder.pending, (state) => {
        state.status = "loading";
      })
      .addCase(createNewOrder.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.order = action.payload;
      })
      .addCase(createNewOrder.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
      .addCase(fetchOrdersByUserId.pending, (state) => {
        state.status = "loading";
      })
      .addCase(fetchOrdersByUserId.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.orders = action.payload;
      })
      .addCase(fetchOrdersByUserId.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
      .addCase(fetchAllOrders.pending, (state) => {
        state.status = "loading";
      })
      .addCase(fetchAllOrders.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.orders = action.payload; // Gán danh sách orders vào state
      })
      .addCase(fetchAllOrders.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
      // Trạng thái khi cập nhật đơn hàng đang được thực hiện
      .addCase(updateOrderStatusThunk.pending, (state) => {
        state.status = 'loading';
      })
      // Trạng thái khi cập nhật thành công
      .addCase(updateOrderStatusThunk.fulfilled, (state, action) => {
        state.status = 'succeeded';
        const { orderId, status } = action.payload;
        const order = state.orders.find((o) => o.orderId === orderId);
        if (order) {
          order.orderStatus = status; // Cập nhật trạng thái trong state
        }
      })
      // Trạng thái khi gặp lỗi
      .addCase(updateOrderStatusThunk.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload;
      });
  },
});

export default ordersSlice.reducer;
