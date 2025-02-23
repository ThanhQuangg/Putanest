import React, { createContext, useState, useEffect } from "react";

// Tạo Context
export const CartContext = createContext();

const CartProvider = ({ children }) => {
    // Lấy dữ liệu từ localStorage (nếu có)
    const storedCart = JSON.parse(localStorage.getItem("cart")) || [];

    const [cart, setCart] = useState(storedCart);

    // Cập nhật localStorage khi giỏ hàng thay đổi
    useEffect(() => {
        localStorage.setItem("cart", JSON.stringify(cart));
    }, [cart]);

    // Hàm thêm sản phẩm vào giỏ hàng
    const addToCart = (product) => {
        const updatedCart = [...cart, product];
        setCart(updatedCart);
    };

    // Hàm xóa sản phẩm khỏi giỏ hàng
    const removeFromCart = (productId) => {
        const updatedCart = cart.filter((item) => item.id !== productId);
        setCart(updatedCart);
    };

    return (
        <CartContext.Provider value={{ cart, addToCart, removeFromCart }}>
            {children}
        </CartContext.Provider>
    );
};

export default CartProvider;
