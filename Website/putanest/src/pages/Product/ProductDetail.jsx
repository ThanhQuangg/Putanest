import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchProductDetail } from "../../features/products/productSlice";
import { addToCart } from "../../features/cart/cartSlice";
import { fetchCartByUserId } from "../../features/cart/cartSlice";
import { addCartDetail } from "../../features/cart/cartDetailsSlice";
import { useParams } from "react-router-dom";
import "../../styles/ProductDetail.scss";
import MainLayout from "../../layouts/MainLayout";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const ProductDetail = () => {
  const { id } = useParams();
  const dispatch = useDispatch();
  const { selectedProduct, loading, error } = useSelector(
    (state) => state.products
  );
  const [quantity, setQuantity] = useState(1);
  const [isImageModalOpen, setImageModalOpen] = useState(false);
  const [carts, setCarts] = useState([]);
  const [currentUser, setCurrentUser] = useState(null);

  useEffect(() => {
    dispatch(fetchProductDetail(id));
  }, [dispatch, id]);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      try {
        const decodedToken = JSON.parse(atob(token.split(".")[1])); // Giải mã token
        const userId = decodedToken?.userId;

        if (userId) {
          setCurrentUser(userId); // Cập nhật trạng thái currentUser
          //   setCartStatus("loading");
          dispatch(fetchCartByUserId(userId))
            .unwrap()
            .then((response) => {
              setCarts([response]); // Đảm bảo rằng phản hồi được lưu trữ dưới dạng mảng
              //   setCartStatus("succeeded");
            })
            .catch((error) => {
              //   setCartStatus("failed");
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

  const cartId = carts.length > 0 ? carts[0].cartId : null;
  console.log("Cart ID:", cartId);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  if (!selectedProduct) {
    return <div>Product not found.</div>;
  }

  const handleImageClick = () => {
    setImageModalOpen(true);
  };

  const handleCloseModal = () => {
    setImageModalOpen(false);
  };

  const handleAddToCart = () => {
    dispatch(
      addCartDetail({
        cartId: cartId,
        productId: selectedProduct.productId,
        productName: selectedProduct.productName,
        price: selectedProduct.price,
        avatar: selectedProduct.avatar,
        quantity,
      })
    );
    toast.success("Sản phẩm đã được thêm vào giỏ hàng!", {
      position: "top-right",
      autoClose: 2000, 
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      theme: "colored",
    });
  };
  return (
    <MainLayout>
      <div className="product-detail">
        <h1>{selectedProduct.productName}</h1>
        <img
          className="product-image"
          src={selectedProduct.avatar || "/assets/image/avatar.png"}
          alt={selectedProduct.productName}
          onClick={handleImageClick}
        />
        <p className="product-price">
          Giá:{" "}
          {selectedProduct.price.toLocaleString("vi-VN", {
            style: "currency",
            currency: "VND",
          })}
        </p>
        <p className="product-description">
          Chi tiết sản phẩm: {selectedProduct.description}
        </p>
        <p className="product-category">
          Loại sản phẩm: {selectedProduct.categoryName}
        </p>
        <p>Số lượng</p>
        <div>
          <input
            type="number"
            min="1"
            value={quantity}
            onChange={(e) => setQuantity(Number(e.target.value))}
            style={{ width: "60px", marginRight: "10px" }}
          />
          <button onClick={handleAddToCart}>Thêm vào giỏ hàng</button>
        </div>

        {/* Modal for Image */}
        {isImageModalOpen && (
          <div className="image-modal" onClick={handleCloseModal}>
            <img
              src={selectedProduct.avatar || "/assets/image/avatar.png"}
              alt="Phóng to sản phẩm"
              className="modal-image"
            />
          </div>
        )}
      </div>
    </MainLayout>
  );
};

export default ProductDetail;
