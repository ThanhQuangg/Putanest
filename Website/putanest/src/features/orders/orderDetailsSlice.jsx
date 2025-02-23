import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { getOrderDetailsByOrderId } from '../../utils/API/orderDetailsApi';

// Thunk để gọi API lấy chi tiết đơn hàng
export const fetchOrderDetails = createAsyncThunk(
  'orderDetails/fetchByOrderId',
  async (orderId, { rejectWithValue }) => {
    try {
      const response = await getOrderDetailsByOrderId(orderId);
      return response;
    } catch (error) {
      return rejectWithValue(error.response?.data || 'Lỗi khi lấy chi tiết đơn hàng');
    }
  }
);

// Tạo slice cho orderDetails
const orderDetailsSlice = createSlice({
  name: 'orderDetails',
  initialState: {
    orderDetails: [],
    status: 'idle', 
    error: null,
  },
  reducers: {
    clearOrderDetails: (state) => {
      state.orderDetails = [];
      state.status = 'idle';
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchOrderDetails.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchOrderDetails.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.orderDetails = action.payload;
      })
      .addCase(fetchOrderDetails.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload;
      });
  },
});

export const { clearOrderDetails } = orderDetailsSlice.actions;
export default orderDetailsSlice.reducer;

// Selector
export const selectOrderDetails = (state) => state.orderDetails.orderDetails;
export const selectOrderDetailsStatus = (state) => state.orderDetails.status;
export const selectOrderDetailsError = (state) => state.orderDetails.error;
