// import React, { useEffect } from "react";
// import { useDispatch, useSelector } from "react-redux";
// import { fetchOrdersByUserId } from "../../features/orders/orderSlice";
// import MainLayout from "../../layouts/MainLayout";
// import "../../styles/OrderPage.scss";

// const UserOrdersPage = () => {
//   const dispatch = useDispatch();

//   // Lấy dữ liệu từ Redux store
//   const { orders, status, error } = useSelector((state) => state.orders);
//   const userId = useSelector((state) => state.auth.userId);

//   // Gọi API lấy đơn hàng khi userId thay đổi
//   useEffect(
//     () => {
//       const token = localStorage.getItem("token");
//       if (token) {
//         const decodedToken = JSON.parse(atob(token.split(".")[1])); // Giải mã token
//         const userId = decodedToken?.userId;

//         if (userId) {
//           dispatch(fetchOrdersByUserId(userId));
//         }
//       }
//     },
//     [dispatch],
//     userId
//   );
//   console.log("userId:", userId);

//   if (status === "loading") return <div>Đang tải dữ liệu đơn hàng...</div>;

//   if (status === "failed")
//     return <div>Lỗi: {error || "Không thể tải dữ liệu."}</div>;

//   return (
//     <MainLayout>
//       <div>
//         <h1 style={{textAlign:"center",fontSize:50, fontWeight: "bold",color: "darkblue"}}>Lịch sử mua hàng</h1>

//         {orders.length > 0 ? (
//           <div className="order-list">
//             {orders.map((order, index) => (
//               <div key={order.orderId} className="order-item">
//                 <h2>Đơn hàng {index+1}</h2>
//                 <p>
//                   <strong>Tổng tiền:</strong> {order.totalAmount.toLocaleString("vi-VN", {
//                       style: "currency",
//                       currency: "VND",
//                     })}
//                 </p>
//                 <p>
//                   <strong>Trạng thái:</strong> {order.orderStatus}
//                 </p>
//                 <p>
//                   <strong>Ngày tạo:</strong>{" "}
//                   {new Date(order.createdAt).toLocaleString()}
//                 </p>
//                 {/* <p>
//                   <strong>Ngày cập nhật:</strong>{" "}
//                   {new Date(order.updatedAt).toLocaleString()}
//                 </p> */}
//               </div>
//             ))}
//           </div>
//         ) : (
//           <p>Chưa có đơn hàng nào.</p>
//         )}
//       </div>
//     </MainLayout>
//   );
// };

// export default UserOrdersPage;

import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchOrdersByUserId } from "../../features/orders/orderSlice";
import {
  fetchOrderDetails,
  clearOrderDetails,
} from "../../features/orders/orderDetailsSlice";
import MainLayout from "../../layouts/MainLayout";
import "../../styles/OrderPage.scss";

const UserOrdersPage = () => {
  const dispatch = useDispatch();

  // Lấy dữ liệu từ Redux store
  const { orders, status, error } = useSelector((state) => state.orders);
  const {
    orderDetails,
    status: detailsStatus,
    error: detailsError,
  } = useSelector((state) => state.orderDetails );
  const userId = useSelector((state) => state.auth.userId);

  // State theo dõi đơn hàng đang mở
  const [expandedOrderId, setExpandedOrderId] = useState(null);

  // Gọi API lấy đơn hàng khi userId thay đổi
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      const decodedToken = JSON.parse(atob(token.split(".")[1])); // Giải mã token
      const userId = decodedToken?.userId;

      if (userId) {
        dispatch(fetchOrdersByUserId(userId));
      }
    }
  }, [dispatch]);

  // Xử lý khi click vào đơn hàng
  const handleOrderClick = (orderId) => {
    if (expandedOrderId === orderId) {
      setExpandedOrderId(null);
      dispatch(clearOrderDetails());
    } else {
      setExpandedOrderId(orderId);
      dispatch(fetchOrderDetails(orderId));
    }
  };

  if (status === "loading") return <div>Đang tải dữ liệu đơn hàng...</div>;
  if (status === "failed")
    return <div>Lỗi: {error || "Không thể tải dữ liệu."}</div>;

  return (
    <MainLayout>
      <div>
        <h1
          style={{
            textAlign: "center",
            fontSize: 50,
            fontWeight: "bold",
            color: "darkblue",
          }}
        >
          Lịch sử mua hàng
        </h1>

        {orders.length > 0 ? (
          <div className="order-list">
            {orders.map((order, index) => (
              <div
                key={order.orderId}
                className="order-item"
                // onClick={() => handleOrderClick(order.orderId)}
              >
                <h2>Đơn hàng {index + 1}</h2>
                <p>
                  <strong>Tổng tiền:</strong>{" "}
                  {order.totalAmount.toLocaleString("vi-VN", {
                    style: "currency",
                    currency: "VND",
                  })}
                </p>
                <p>
                  <strong>Trạng thái:</strong> {order.orderStatus}
                </p>
                <p>
                  <strong>Ngày tạo:</strong>{" "}
                  {new Date(order.createdAt).toLocaleString()}
                </p>
                <button onClick={() => handleOrderClick(order.orderId)}>
                  Chi tiết đơn hàng
                </button>

                {/* Hiển thị chi tiết đơn hàng khi được mở */}
                {expandedOrderId === order.orderId && (
                  <div className="order-details">
                    {detailsStatus === "loading" ? (
                      <p>Đang tải chi tiết đơn hàng...</p>
                    ) : Array.isArray(orderDetails) && orderDetails.length > 0
                    ? (
                      orderDetails.map((detail) => (
                        <div key={detail.id} className="detail-item">
                          <p>
                            <strong>Sản phẩm:</strong> {detail.productName}
                          </p>
                          <p>
                            <strong>Số lượng:</strong> {detail.quantity}
                          </p>
                          <p>
                            <strong>Giá:</strong>{" "}
                            {detail.price.toLocaleString("vi-VN", {
                              style: "currency",
                              currency: "VND",
                            })}
                          </p>
                        </div>
                      ))
                    ) : (
                      <p>Không có chi tiết đơn hàng.</p>
                    )}
                  </div>
                )}
              </div>
            ))}
          </div>
        ) : (
          <p>Chưa có đơn hàng nào.</p>
        )}
      </div>
    </MainLayout>
  );
};

export default UserOrdersPage;
