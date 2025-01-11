import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { createOrder, getOrdersByUserId } from '../../utils/API/orderApi';

// Thunk để tạo đơn hàng mới
export const createNewOrder = createAsyncThunk('orders/create', async (orderRequest) => {
    const response = await createOrder(orderRequest);
    return response;
});

// Thunk để lấy đơn hàng theo userId
export const fetchOrdersByUserId = createAsyncThunk('orders/fetchByUserId', async (userId) => {
    const response = await getOrdersByUserId(userId);
    return response;
});

const ordersSlice = createSlice({
    name: 'orders',
    initialState: {
        orders: [],
        order: null,
        status: 'idle',
        error: null
    },
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(createNewOrder.pending, (state) => {
                state.status = 'loading';
            })
            .addCase(createNewOrder.fulfilled, (state, action) => {
                state.status = 'succeeded';
                state.order = action.payload;
            })
            .addCase(createNewOrder.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
            })
            .addCase(fetchOrdersByUserId.pending, (state) => {
                state.status = 'loading';
            })
            .addCase(fetchOrdersByUserId.fulfilled, (state, action) => {
                state.status = 'succeeded';
                state.orders = action.payload;
            })
            .addCase(fetchOrdersByUserId.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
            });
    }
});

export default ordersSlice.reducer;