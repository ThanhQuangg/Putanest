import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import MainLayout from "../../layouts/MainLayout";
import {
  fetchOrdersByUserId,
  fetchAllOrders,
  fetchAllOrdersPagination,
  updateOrderStatusThunk,
} from "../../features/orders/orderSlice";
import { fetchOrderDetails } from "../../features/orders/orderDetailsSlice";
import "../../styles/AdminOrderManage.scss";

const AdminOrderManagement = () => {
  const dispatch = useDispatch();
  const { orders, status, error } = useSelector((state) => state.orders);

  const {
    orderDetails,
    status: detailsStatus,
    error: detailsError,
  } = useSelector((state) => state.orderDetails);

  const [userId, setUserId] = useState("");

  const [expandedOrderId, setExpandedOrderId] = useState(null);

  const [editingOrderId, setEditingOrderId] = useState(null);
  const [newStatus, setNewStatus] = useState("");

  const [currentPage, setCurrentPage] = useState(0);
  const [pageSize, setPageSize] = useState(2);

  useEffect(() => {
    dispatch(fetchAllOrders())
      .unwrap()
      .then((data) => {})
      .catch((err) => {
        console.error("Error fetching orders:", err);
      });
  }, [dispatch]);

  // useEffect(() => {
  //   dispatch(fetchAllOrdersPagination({ page: currentPage, size: pageSize }));
  // }, [dispatch, currentPage, pageSize]);

  const handlePageChange = (newPage) => {
    setCurrentPage(newPage);
  };

  const handleSearch = (e) => {
    e.preventDefault();
    if (userId.trim()) {
      dispatch(fetchOrdersByUserId(userId));
    }
  };

  const handleViewDetails = (orderId) => {
    if (expandedOrderId === orderId) {
      setExpandedOrderId(null);
    } else {
      setExpandedOrderId(orderId);
      dispatch(fetchOrderDetails(orderId));
    }
  };

  const handleEditOrder = (orderId) => {
    setEditingOrderId(orderId); // Hiển thị form chỉnh sửa cho orderId này
  };

  const handleUpdateOrderStatus = (orderId) => {
    if (!newStatus) {
      alert("Vui lòng chọn trạng thái mới!");
      return;
    }
    dispatch(updateOrderStatusThunk({ orderId, status: newStatus }))
      .unwrap()
      .then(() => {
        alert("Cập nhật trạng thái thành công!");
        setEditingOrderId(null); // Ẩn form chỉnh sửa
        setNewStatus(""); // Reset trạng thái
      })
      .catch((err) => {
        console.error("Lỗi khi cập nhật trạng thái:", err);
        alert("Cập nhật thất bại, vui lòng thử lại!");
      });
  };

  return (
    <>
      <MainLayout>
        <div className="admin-order-management">
          <h1>Quản lý đơn hàng</h1>
          <form className="search-form" onSubmit={handleSearch}>
            <input
              type="text"
              placeholder="Nhập User ID"
              value={userId}
              onChange={(e) => setUserId(e.target.value)}
              required
            />
            <button type="submit">Tìm kiếm</button>
          </form>

          {status === "loading" ? (
            <p>Đang tải dữ liệu...</p>
          ) : status === "failed" ? (
            <p className="error">Lỗi: {error}</p>
          ) : (
            <table className="order-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>User ID</th>
                  <th>Ngày tạo</th>
                  <th>Trạng thái</th>
                  <th>Chi tiết</th>
                  <th>Cập nhật trạng thái</th>
                </tr>
              </thead>
              <tbody>
                {orders.map((order) => (
                  <>
                    <tr key={order.orderId}>
                      <td>{order.orderId}</td>
                      <td>{order.userId}</td>
                      <td>
                        {new Date(order.createdAt).toLocaleString("vi-VN")}
                      </td>
                      <td>{order.orderStatus}</td>
                      <td>
                        <button
                          onClick={() => handleViewDetails(order.orderId)}
                        >
                          {expandedOrderId === order.orderId &&
                          detailsStatus === "loading"
                            ? "Đang tải..."
                            : "Xem chi tiết"}
                        </button>
                      </td>
                      <td>
                        {editingOrderId === order.orderId ? (
                          <div className="edit-status-form">
                            <select
                              value={newStatus}
                              onChange={(e) => setNewStatus(e.target.value)}
                            >
                              <option value="">Chọn trạng thái</option>
                              <option value="Đã đóng hàng">Đã đóng hàng</option>
                              <option value="Đã giao cho đơn vị vận chuyển">
                                Đã giao cho đơn vị vận chuyển
                              </option>
                              <option value="Đã hủy">Đã hủy</option>
                            </select>
                            <button
                              onClick={() =>
                                handleUpdateOrderStatus(order.orderId)
                              }
                            >
                              Lưu
                            </button>
                            <button onClick={() => setEditingOrderId(null)}>
                              Hủy
                            </button>
                          </div>
                        ) : (
                          <button
                            onClick={() => handleEditOrder(order.orderId)}
                          >
                            Cập nhật
                          </button>
                        )}
                      </td>
                    </tr>
                    {expandedOrderId === order.orderId &&
                      detailsStatus === "succeeded" && (
                        <tr className="order-details-row">
                          <td colSpan="5">
                            <div className="order-details">
                              <h3>Chi tiết đơn hàng</h3>
                              <ul>
                                {orderDetails.map((detail) => (
                                  <li key={detail.orderDetailId}>
                                    <p>Tên sản phẩm: {detail.productName}</p>
                                    <p>Số lượng: {detail.quantity}</p>
                                    <p>
                                      Giá:{" "}
                                      {detail.price.toLocaleString("vi-VN", {
                                        style: "currency",
                                        currency: "VND",
                                      })}
                                    </p>
                                  </li>
                                ))}
                              </ul>
                            </div>
                          </td>
                        </tr>
                      )}
                    {expandedOrderId === order.orderId &&
                      detailsStatus === "failed" && (
                        <tr className="order-details-row">
                          <td colSpan="5">
                            <p className="error">Lỗi: {detailsError}</p>
                          </td>
                        </tr>
                      )}
                  </>
                ))}
              </tbody>
            </table>
          )}
          <div className="pagination">
            <button
              onClick={() => handlePageChange(currentPage - 1)}
              disabled={currentPage === 0}
            >
              Trước
            </button>
            <span>
              Trang {currentPage + 1} / {orders.totalPages}
            </span>
            <button
              onClick={() => handlePageChange(currentPage + 1)}
              disabled={currentPage === orders.totalPages - 1}
            >
              Tiếp
            </button>
          </div>
        </div>
      </MainLayout>
    </>
  );
};

export default AdminOrderManagement;
