import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { createGuestOrder, getAllGuestOrders, getGuestOrderById, deleteGuestOrder } from '../../utils/API/guestOrderApi';

// Thunk: Lấy tất cả đơn hàng
export const fetchGuestOrders = createAsyncThunk(
  'guestOrders/fetchAll',
  async (_, { rejectWithValue }) => {
    try {
      return await getAllGuestOrders();
    } catch (error) {
      return rejectWithValue(error.response?.data || 'Lỗi khi tải đơn hàng');
    }
  }
);

// Thunk: Tạo đơn hàng mới
export const addGuestOrder = createAsyncThunk(
  'guestOrders/add',
  async (orderData, { rejectWithValue }) => {
    try {
      return await createGuestOrder(orderData);
    } catch (error) {
      return rejectWithValue(error.response?.data || 'Lỗi khi tạo đơn hàng');
    }
  }
);

// Thunk: Xóa đơn hàng
export const removeGuestOrder = createAsyncThunk(
  'guestOrders/delete',
  async (id, { rejectWithValue }) => {
    try {
      await deleteGuestOrder(id);
      return id; // Trả về id để cập nhật store
    } catch (error) {
      return rejectWithValue(error.response?.data || 'Lỗi khi xóa đơn hàng');
    }
  }
);

const guestOrderSlice = createSlice({
  name: 'guestOrders',
  initialState: {
    orders: [],
    status: 'idle',
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      // Lấy tất cả đơn hàng
      .addCase(fetchGuestOrders.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchGuestOrders.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.orders = action.payload;
      })
      .addCase(fetchGuestOrders.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload;
      })

      // Tạo đơn hàng
      .addCase(addGuestOrder.fulfilled, (state, action) => {
        state.orders.push(action.payload);
      })

      // Xóa đơn hàng
      .addCase(removeGuestOrder.fulfilled, (state, action) => {
        state.orders = state.orders.filter((order) => order.id !== action.payload);
      });
  },
});

export default guestOrderSlice.reducer;