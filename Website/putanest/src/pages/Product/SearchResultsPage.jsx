import React from 'react';
import { useLocation } from 'react-router-dom';
import '../../styles/SearchResult.scss';
import MainLayout from '../../layouts/MainLayout';
import { useNavigate } from 'react-router-dom';

const SearchResultsPage = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const { searchResults } = location.state || { searchResults: [] };


  const results = Array.isArray(searchResults) ? searchResults : []; // Đảm bảo là mảng
  const handleProductClick = (productId) => {
    navigate(`/products/${productId}`);
  };

  return (
    <MainLayout>
      <div className='search-results-page'>
        <h2>Kết quả tìm kiếm</h2>
        {results.length > 0 ? (
          <div className="product-list">
            {results.map((product) => (
              <div key={product.id} className="product-card" onClick={() => handleProductClick(product.productId)}>
                <img src={product.avatar} alt={product.name} />
                <h5>{product.productName}</h5>
                <p>Giá: {product.price.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })}</p>
                {/* <button style={{ marginLeft: "10px" }}>Thêm vào giỏ hàng</button> */}
              </div>
            ))}
          </div>
        ) : (
          <p>Không tìm thấy sản phẩm nào.</p>
        )}
      </div>
    </MainLayout>
  );
};

export default SearchResultsPage;
