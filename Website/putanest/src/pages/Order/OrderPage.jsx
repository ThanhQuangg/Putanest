import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchOrdersByUserId } from '../../features/orders/orderSlice';

const UserOrdersPage = () => {
    const dispatch = useDispatch();
    const orders = useSelector(state => state.orders.orders);
    const status = useSelector(state => state.orders.status);
    const error = useSelector(state => state.orders.error);
    const userId = useSelector(state => state.auth.userId); // Giả sử bạn lưu userId trong auth slice

    useEffect(() => {
        if (userId) {
            dispatch(fetchOrdersByUserId(userId));
        }
    }, [dispatch, userId]);

    if (status === 'loading') {
        return <div>Loading...</div>;
    }

    if (status === 'failed') {
        return <div>Error: {error}</div>;
    }

    return (
        <div>
            <h1>Your Orders</h1>
            {orders.length > 0 ? (
                <ul>
                    {orders.map(order => (
                        <li key={order.orderId}>
                            <div>Order ID: {order.orderId}</div>
                            <div>Total Amount: ${order.totalAmount}</div>
                            <div>Status: {order.orderStatus}</div>
                            <div>Created At: {new Date(order.createdAt).toLocaleString()}</div>
                            <div>Updated At: {new Date(order.updatedAt).toLocaleString()}</div>
                        </li>
                    ))}
                </ul>
            ) : (
                <p>No orders found for this user.</p>
            )}
        </div>
    );
};

export default UserOrdersPage;