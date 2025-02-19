import React, { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { fetchCartByUserId } from "../../features/cart/cartSlice";
import {
  fetchCartDetailsByCartId,
  fetchTotalPrice,
  removeCartDetail,
} from "../../features/cart/cartDetailsSlice";
import { createNewOrder } from "../../features/orders/orderSlice";
import "../../styles/Carts.scss";
import MainLayout from "../../layouts/MainLayout";
import payment from "../../assets/image/avatar.png";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

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
  const [selectedItems, setSelectedItems] = useState([]);

  // Lấy userId từ token và gọi API
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      try {
        const decodedToken = JSON.parse(atob(token.split(".")[1])); // Giải mã token
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
            setCartDetails([]);
            setCartDetailsStatus("succeeded");
           
          });
      }
    }
  }, [dispatch, carts]);

  const handlePayment = () => {
    setShowImage(true); // Hiển thị ảnh
    setShowConfirmation(true); // Hiển thị thông báo và các nút
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

  const handleQuantityChange = (cartDetailId, newQuantity) => {
    // Đảm bảo số lượng hợp lệ (>= 1)
    const validQuantity = parseInt(newQuantity, 10);
    if (isNaN(validQuantity) || validQuantity < 1) return;

    // Tìm chi tiết giỏ hàng cần cập nhật
    const updatedDetail = cartDetails.find(
      (detail) => detail.cartDetailId === cartDetailId
    );
    if (!updatedDetail) return;

    // Chuẩn bị dữ liệu gửi lên API
    const updatedCartDetail = {
      ...updatedDetail,
      quantity: validQuantity,
    };

    // Gửi yêu cầu cập nhật lên API
    fetch(
      `https://putanest-h9ou.onrender.com/api/cartdetails/${cartDetailId}`,
      {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(updatedCartDetail),
      }
    )
      .then((response) => {
        if (!response.ok) {
          throw new Error("Cập nhật thất bại");
        }
        return response.json();
      })
      .then((updatedResponse) => {
        // Cập nhật lại trạng thái giỏ hàng
        setCartDetails((prevDetails) =>
          prevDetails.map((detail) =>
            detail.cartDetailId === cartDetailId ? updatedResponse : detail
          )
        );

        // Cập nhật lại tổng giá trị giỏ hàng
        const updatedTotal = cartDetails.reduce((sum, detail) => {
          return (
            sum +
            detail.price *
              (detail.cartDetailId === cartDetailId
                ? validQuantity
                : detail.quantity)
          );
        }, 0);
        setTotalPrice(updatedTotal);
      })
      .catch((error) => console.error("Error updating quantity:", error));
  };

  // Hàm xử lý chọn/bỏ chọn sản phẩm
  const handleSelectItem = (cartDetailId) => {
    setSelectedItems((prevSelected) => {
      if (prevSelected.includes(cartDetailId)) {
        return prevSelected.filter((id) => id !== cartDetailId);
      } else {
        return [...prevSelected, cartDetailId];
      }
    });
  };

  // Tính tổng tiền chỉ dựa trên sản phẩm đã chọn
  const selectedTotalPrice = cartDetails
    .filter((detail) => selectedItems.includes(detail.cartDetailId))
    .reduce((sum, detail) => sum + detail.price * detail.quantity, 0);

  // Thanh toán chỉ những sản phẩm đã chọn
  const handleConfirmPayment = async () => {
    const orderRequest = {
      userId: currentUser,
      orderDetails: cartDetails
        .filter((detail) => selectedItems.includes(detail.cartDetailId))
        .map((detail) => ({
          productId: detail.productId,
          quantity: detail.quantity,
          price: detail.price,
        })),
    };

    try {
      await dispatch(createNewOrder(orderRequest)).unwrap();
      setShowImage(false);
      setShowConfirmation(false);

      // Đợi tất cả các mục được xóa hoàn tất trước khi cập nhật giỏ hàng
      await Promise.all(
        selectedItems.map((cartDetailId) =>
          dispatch(removeCartDetail(cartDetailId)).unwrap()
        )
      );

      // Cập nhật lại giỏ hàng sau khi xóa
      if (carts.length > 0) {
        await dispatch(fetchCartDetailsByCartId(carts[0].cartId)).unwrap();
      }

      // Xóa danh sách sản phẩm đã chọn
      setSelectedItems([]);

      // Cập nhật lại trạng thái cartDetails
      setCartDetails((prevDetails) =>
        prevDetails.filter(
          (detail) => !selectedItems.includes(detail.cartDetailId)
        )
      );

      toast.success("Sản phẩm đã được đặt thành công!", {
        position: "top-right",
        autoClose: 3000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        theme: "colored",
      });
    } catch (error) {
      console.error("Error creating order:", error);
    }
  };
  return (
    <MainLayout>
      <div className="cart-page">
        <h1>Giỏ hàng</h1>
        <h2>Thông tin giỏ hàng</h2>
        {cartDetails.length > 0 ? (
          <table className="cart-details-table">
            <thead>
              <tr>
                <th>Tên sản phẩm</th>
                <th>Số lượng</th>
                <th>Giá</th>
                <th>Tổng cộng</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {cartDetails.map((detail) => (
                <tr key={detail.cartDetailId}>
                  <td>{detail.productName}</td>
                  <td>
                    <input
                      type="number"
                      value={detail.quantity}
                      min="1"
                      style={{ width: "50px", textAlign: "center" }}
                      onChange={(e) =>
                        handleQuantityChange(
                          detail.cartDetailId,
                          e.target.value
                        )
                      }
                    />
                  </td>
                  <td>
                    {detail.price.toLocaleString("vi-VN", {
                      style: "currency",
                      currency: "VND",
                    })}
                  </td>
                  <td>
                    {(detail.price * detail.quantity).toLocaleString("vi-VN", {
                      style: "currency",
                      currency: "VND",
                    })}
                  </td>
                  <td>
                    <input
                      type="checkbox"
                      checked={selectedItems.includes(detail.cartDetailId)}
                      onChange={() => handleSelectItem(detail.cartDetailId)}
                    />
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        ) : (
          <p>Không có sản phẩm nào trong giỏ hàng.</p>
        )}

        <div className="cart-total">
          <h3>
            Tổng:{" "}
            {selectedTotalPrice.toLocaleString("vi-VN", {
              style: "currency",
              currency: "VND",
            })}
          </h3>
        </div>
        {selectedItems.length > 0 && (
          <button className="checkout-button" onClick={handleConfirmPayment}>
            Thanh toán sản phẩm đã chọn
          </button>
        )}
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
