import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getProductsByCategory } from "../../utils/API/product";
import { useNavigate } from 'react-router-dom';
import '../../styles/CategoryPage.scss'
import MainLayout from '../../layouts/MainLayout';

const CategoryPage = () => {
  const { categoryId } = useParams(); // Lấy ID từ URL
  const [products, setProducts] = useState([]);
  const [categoryName, setCategoryName] = useState('');
  const navigate = useNavigate();

  const handleImageClick = () => {
    setImageModalOpen(true);
  };

  const handleProductClick = (productId) => {
    navigate(`/products/${productId}`);
};

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const data = await getProductsByCategory(categoryId);
        setProducts(data);
        setCategoryName(data.categoryName);
      } catch (error) {
        console.error("Lỗi khi lấy sản phẩm:", error);
      }
    };
    fetchProducts();
  }, [categoryId]);

  return (
    <MainLayout>
      <div className="category-page">
        <h1>Danh sách sản phẩm {categoryName}</h1>
        <div className="product-list">
          {products.length > 0 ? (
            products.map((product) => (
              <div key={product.id} className="product-item" onClick={() => handleProductClick(product.productId)}>
                
                <img
                  className="product-image"
                  src={product.avatar || '/assets/image/avatar.png'}
                  alt={product.productName}
                  onClick={handleImageClick}
                />
                <h3>{product.productName}</h3>
                <p className="product-price">Giá: {product.price.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })}</p>
                {/* <button onClick={() => handleProductClick(product.productId)}>Xem chi tiết</button> */}
                <button style={{ marginLeft: "10px" }}>Thêm vào giỏ hàng</button>
                {/* <p className="product-description">Chi tiết sản phẩm: {product.description}</p>
                <p>Giá: {product.price} VND</p> */} 

              </div>
            ))
          ) : (
            <p>Không có sản phẩm nào!</p>
          )}
        </div>
      </div>
    </MainLayout>
  );
};

export default CategoryPage;
