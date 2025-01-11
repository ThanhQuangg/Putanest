import React, { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { fetchCartByUserId } from "../../features/cart/cartSlice";
import { fetchCartDetailsByCartId, fetchTotalPrice } from "../../features/cart/cartDetailsSlice";
import { createNewOrder } from "../../features/orders/orderSlice";
import { removeCartDetail } from "../../features/cart/cartDetailsSlice";
import "../../styles/Carts.scss";
import MainLayout from '../../layouts/MainLayout';
import payment from '../../assets/image/avatar.png';

const CartPage = () => {
  const dispatch = useDispatch();
  const [carts, setCarts] = useState([]);
  const [cartDetails, setCartDetails] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0);
  const [cartStatus, setCartStatus] = useState("idle");
  const [cartDetailsStatus, setCartDetailsStatus] = useState("idle");
  const [currentUser, setCurrentUser] = useState(null);
  const [showImage, setShowImage] = useState(false);
  const [showConfirmation, setShowConfirmation] = useState(false);

  // Lấy userId từ token và gọi API
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      try {
        const decodedToken = JSON.parse(atob(token.split('.')[1])); // Giải mã token
        const userId = decodedToken?.userId;

        if (userId) {
          setCurrentUser(userId); // Cập nhật trạng thái currentUser
          setCartStatus("loading");
          dispatch(fetchCartByUserId(userId))
            .unwrap()
            .then((response) => {
              setCarts([response]); // Đảm bảo rằng phản hồi được lưu trữ dưới dạng mảng
              setCartStatus("succeeded");
            })
            .catch((error) => {
              setCartStatus("failed");
              console.error("Error fetching cart:", error);
            });
        }
      } catch (error) {
        console.error("Invalid token:", error);
      }
    } else {
      console.error("No token found in localStorage");
    }
  }, [dispatch]);

  // Lấy chi tiết giỏ hàng theo cartId
  useEffect(() => {
    if (carts.length > 0) {
      const cartId = carts[0].cartId; // Sử dụng carts[0].cartId
      if (cartId) {
        setCartDetailsStatus("loading");
        dispatch(fetchCartDetailsByCartId(cartId))
          .unwrap()
          .then((response) => {
            setCartDetails(response);
            setCartDetailsStatus("succeeded");
          })
          .catch((error) => {
            setCartDetailsStatus("failed");
            console.error("Error fetching cart details:", error);
          });

        dispatch(fetchTotalPrice(cartId))
          .unwrap()
          .then((response) => {
            setTotalPrice(response);
          })
          .catch((error) => {
            console.error("Error fetching total price:", error);
          });
      }
    }
  }, [dispatch, carts]);

  const handlePayment = () => {
    setShowImage(true); // Hiển thị ảnh
    setShowConfirmation(true); // Hiển thị thông báo và các nút
  };

  const handleConfirmPayment = async () => {
    const orderRequest = {
      userId: currentUser,
      orderDetails: cartDetails.map(detail => ({
        productId: detail.productId,
        quantity: detail.quantity,
        price: detail.price
      }))
    };
    try {
      await dispatch(createNewOrder(orderRequest)).unwrap();
      setShowImage(false);
      setShowConfirmation(false);
      // Xóa các chi tiết giỏ hàng sau khi tạo đơn hàng thành công
      cartDetails.forEach((detail) => {  
      dispatch(removeCartDetail(detail.cartDetailId));
    });
      // Xử lý sau khi tạo đơn hàng thành công (ví dụ: điều hướng đến trang xác nhận)
    } catch (error) {
      console.error("Error creating order:", error);
    }
  };

  const handleCancelPayment = () => {
    setShowImage(false); // Ẩn ảnh
    setShowConfirmation(false); // Ẩn thông báo và các nút
  };

  if (cartStatus === "loading" || cartDetailsStatus === "loading") {
    return <div>Loading...</div>;
  }
  if (cartStatus === "failed" || cartDetailsStatus === "failed") {
    return <div>Error loading cart data.</div>;
  }

  if (cartStatus === "loading" || cartDetailsStatus === "loading") {
    return <div>Loading...</div>;
  }

  if (cartStatus === "failed" || cartDetailsStatus === "failed") {
    return <div>Error loading cart data.</div>;
  }

  return (
    <MainLayout>
      <div className="cart-page">
        <h1>Cart</h1>

        {currentUser && <p>Current User ID: {currentUser}</p>} {/* Hiển thị userId hiện tại */}

        {carts.length > 0 ? (
          <div className="cart-info">
            <h2>Cart ID: {carts[0].cartId}</h2> {/* Sử dụng carts[0].cartId */}
            <p>User ID: {carts[0].userId}</p>
            <p>Created At: {new Date(carts[0].createdAt).toLocaleDateString()}</p>
          </div>
        ) : (
          <p>No cart found for the current user.</p>
        )}

        <h2>Thông tin đơn hàng</h2>
        {cartDetails.length > 0 ? (
          <table className="cart-details-table">
            <thead>
              <tr>
                {/* <th>ID</th> */}
                <th>Tên sản phẩm</th>
                <th>Số lượng</th>
                <th>Giá</th>
                <th>Tổng cộng</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {cartDetails.map((detail) => (
                <tr key={detail.cartDetailsId}>
                  {/* <td>{detail.cartDetailId}</td>  */}
                  <td>{detail.productName}</td>
                  <td>{detail.quantity}</td>
                  <td>{detail.price.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })}</td>
                  <td>{(detail.price * detail.quantity).toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })}</td>
                  <td>
                    <button
                      className="checkout-button"
                      onClick={() => handleConfirmPayment(detail.cartDetailId)}
                    >
                      Thanh toán
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        ) : (
          <p>No items found in the cart.</p>
        )}

        <div className="cart-total">
          <h3>Total Price: ${totalPrice.toFixed(2)}</h3>
        </div>

        {showImage && (
          <div className="payment-image">
            <img src={payment} alt="Processing Payment" />
          </div>
        )}
        {showConfirmation && (
          <div className="confirmation">
            <p>Đã quét mã?</p>
            <button onClick={handleConfirmPayment}>Đã quét</button>
            <button onClick={handleCancelPayment}>Hủy</button>
          </div>
        )}
      </div>
    </MainLayout>
  );

};

export default CartPage;