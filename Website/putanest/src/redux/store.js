import { configureStore } from '@reduxjs/toolkit';
import cartReducer from '../features/cart/cartSlice';
import cartDetailsReducer from '../features/cart/cartDetailsSlice';
import productReducer from '../features/products/productSlice';
import authReducer from '../features/auth/authSlice';
import orderReducer from '../features/orders/orderSlice';
import guestOrderReducer from '../features/orders/guestOrderSlice';
import orderDetailsReducer from '../features/orders/orderDetailsSlice'
import categoryReducer from '../features/categories/categorySlice';
import userReducer from '../features/users/userSlice';
import uiReducer from '../features/ui/uiSlice';

const store = configureStore({
  reducer: {
    cart: cartReducer,
    cartDetails: cartDetailsReducer,
    products: productReducer,
    auth: authReducer,
    orders: orderReducer,
    orderDetails: orderDetailsReducer,
    categories: categoryReducer,
    users: userReducer,
    ui: uiReducer,
    guestOrder: guestOrderReducer,
  },
});
export default store;