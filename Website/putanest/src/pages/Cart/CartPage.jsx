import React, { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { fetchCartByUserId } from "../../features/cart/cartSlice";
import {
  fetchCartDetailsByCartId,
  fetchTotalPrice,
  removeCartDetail,
} from "../../features/cart/cartDetailsSlice";
import { createNewOrder } from "../../features/orders/orderSlice";
import { addGuestOrder } from "../../features/orders/guestOrderSlice";
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
  const isGuest = !currentUser;
  const [formData, setFormData] = useState({
    customerName: "",
    email: "",
    phoneNumber: "",
    address: "",
  });

  const [guestOrderDetails, setGuestOrderDetails] = useState(
    JSON.parse(localStorage.getItem("guestCart")) || []
  );

  // Kiểm tra token và localStorage để lấy giỏ hàng
  useEffect(() => {
    const token = localStorage.getItem("token");
    const guestCart = localStorage.getItem("guestCart");

    if (token) {
      try {
        const decodedToken = JSON.parse(atob(token.split(".")[1]));
        const userId = decodedToken?.userId;

        if (userId) {
          setCurrentUser(userId);
          setCartStatus("loading");
          dispatch(fetchCartByUserId(userId))
            .unwrap()
            .then((response) => {
              setCarts([response]);
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
    } else if (guestCart) {
      // Nếu không có token nhưng có guestCart => load từ localStorage
      try {
        const parsedGuestCart = JSON.parse(guestCart);
        setCartDetails(parsedGuestCart);
        setCartStatus("succeeded");
      } catch (error) {
        console.error("Error parsing guestCart:", error);
      }
    } else {
      console.error("No token or guestCart found");
    }
  }, [dispatch]);

  // Lấy chi tiết giỏ hàng theo cartId
  useEffect(() => {
    if (carts.length > 0) {
      const cartId = carts[0].cartId;
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
            setCartDetailsStatus("failed");
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

  const updateGuestCart = (updatedCart) => {
    localStorage.setItem("guestCart", JSON.stringify(updatedCart));
  };

  const handleQuantityChange = (cartDetailId, newQuantity) => {
    // Đảm bảo số lượng hợp lệ (>= 1)
    const validQuantity = parseInt(newQuantity, 10);
    if (isNaN(validQuantity) || validQuantity < 1) return;

    // Tìm chi tiết giỏ hàng cần cập nhật
    const updatedDetail = cartDetails.find(
      (detail) => detail.cartDetailId === cartDetailId
    );
    if (!updatedDetail) return;

    const updatedCartDetails = cartDetails.map((detail) =>
      detail.cartDetailId === cartDetailId
        ? { ...detail, quantity: validQuantity }
        : detail
    );

    setCartDetails(updatedCartDetails);
    updateGuestCart(updatedCartDetails); // Cập nhật lại localStorage

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

  //GuestPaymentForm

  // const [formData, setFormData] = useState({
  //   customerName: "",
  //   email: "",
  //   phoneNumber: "",
  //   address: "",
  // });

  // const [guestOrderDetails, setGuestOrderDetails] = useState(
  //   JSON.parse(localStorage.getItem("guestCart")) || []
  // );

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Kiểm tra dữ liệu đầu vào
    if (
      !formData.customerName ||
      !formData.email ||
      !formData.phoneNumber ||
      !formData.address
    ) {
      alert("Vui lòng điền đầy đủ thông tin cá nhân.");
      return;
    }

    const payload = {
      ...formData,
      guestOrderDetails,
    };

    try {
      const response = await fetch("http://localhost:8080/api/guest-orders", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });

      if (response.ok) {
        alert("Thanh toán thành công!");
        // Xóa giỏ hàng sau khi thanh toán thành công
        localStorage.removeItem("guestCart");
        window.location.reload();
      } else {
        alert("Thanh toán thất bại!");
      }
    } catch (error) {
      console.error("Lỗi khi gọi API thanh toán:", error);
      alert("Đã xảy ra lỗi. Vui lòng thử lại.");
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm("Bạn có chắc chắn muốn xóa sản phẩm này?")) {
      try {
        await dispatch(removeCartDetail(id)).unwrap();
        toast.success("Xóa sản phẩm thành công!", { position: "top-right" });

        // Xóa sản phẩm khỏi state cartDetails ngay lập tức để re-render
        setCartDetails((prevDetails) =>
          prevDetails.filter((detail) => detail.cartDetailId !== id)
        );

        // Fetch lại danh sách giỏ hàng để đảm bảo dữ liệu mới nhất
        if (carts.length > 0) {
          const updatedCart = await dispatch(
            fetchCartDetailsByCartId(carts[0].cartId)
          ).unwrap();
          setCartDetails(updatedCart);
        }
      } catch (error) {
        console.error("Lỗi khi xóa hoặc cập nhật giỏ hàng:", error);
        toast.error("Xóa thất bại! Vui lòng thử lại.", {
          position: "top-right",
        });
      }
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
                  <button onClick={() => handleDelete(detail.cartDetailId)}>
                    Xóa
                  </button>
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
        {selectedItems.length > 0 &&
          (isGuest ? (
            <form onSubmit={handleSubmit}>
              <label>
                Họ và tên:{" "}
                <input
                  type="text"
                  name="customerName"
                  value={formData.customerName}
                  onChange={handleChange}
                  required
                />
              </label>
              <label>
                Email:{" "}
                <input
                  type="email"
                  name="email"
                  value={formData.email}
                  onChange={handleChange}
                  required
                />
              </label>
              <label>
                Số điện thoại:{" "}
                <input
                  type="text"
                  name="phoneNumber"
                  value={formData.phoneNumber}
                  onChange={handleChange}
                  required
                />
              </label>
              <label>
                Địa chỉ:{" "}
                <input
                  type="text"
                  name="address"
                  value={formData.address}
                  onChange={handleChange}
                  required
                />
              </label>
              <button type="submit">Thanh toán</button>
            </form>
          ) : (
            <button onClick={handleConfirmPayment}>Thanh toán</button>
          ))}

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
