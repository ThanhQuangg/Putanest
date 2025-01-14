import React, { useEffect } from 'react';
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import { useDispatch, useSelector } from 'react-redux';
import { fetchProducts } from '../../features/products/productSlice';
import '../../styles/HomePage.scss';
import MainLayout from '../../layouts/MainLayout';
import { useNavigate } from 'react-router-dom';
import backgroundImage from '../../assets/image/background.jpg';
import bannerImage from '../../assets/image/banner.jpg'
import bannerImage1 from '../../assets/image/banner1.png'


const HomePage = () => {
  const dispatch = useDispatch();
  const { products, loading, error } = useSelector((state) => state.products); // Lấy dữ liệu sản phẩm từ store
  const navigate = useNavigate();
  useEffect(() => {
    dispatch(fetchProducts()); // Dispatch action để fetch sản phẩm khi component mount
  }, [dispatch]);

  const handleProductClick = (productId) => {
    navigate(`/products/${productId}`);
  };
  if (loading) return <div>Loading...</div>; // Hiển thị loading khi dữ liệu đang được tải
  if (error) return <div>Error: {error}</div>; // Hiển thị lỗi nếu có

  const bannerImages = [ bannerImage,bannerImage1,backgroundImage];

  console.log("Banner Images:", bannerImages);
  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 2000,
    pauseOnHover: true,
    pauseOnFocus: true,
  };
  return (
    <MainLayout>
      <div className="homepage">
        {/* Banner */}
        <Slider {...settings}>
          {bannerImages.map((image, index) => {
            console.log(`Rendering Banner Image ${index}:`, image);
            return (
              <div key={index}>
                <img
                  className="banner-image"
                  style={{ width: "100%", height: "600px", objectFit: "cover" }}
                  src={image}
                  alt={`Banner ${index + 1}`}
                />
              </div>
            );
          })}
        </Slider>
        <div className="banner">
          <h1>Chào mừng bạn đến với cửa hàng yến Liên Sự</h1>
          <p className="lead">Chất lượng hàng đầu - Dịch vụ uy tín - Giá cả hợp lý</p>
        </div>

        {/* Featured Products */}

        <div className="content" >
          <h2>Sản phẩm nổi bật</h2>
          <div className='products-container'>
            <div className="products" >
              {products.map((product) => (
                <div className="product-card" key={product.productId} onClick={() => handleProductClick(product.productId)}>
                  <img src={product.avatar || "https://via.placeholder.com/300x200"} alt={product.productName} />
                  <h5>{product.productName}</h5>
                  {/* <p>{product.description}</p> */}
                  <p>Giá: {product.price.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })}</p>
                  {/* <button>Thêm vào giở hàng</button> */}
                </div>
              ))}
            </div>
          </div>
        </div>

        {/* About Section */}
        {/* <div className="about">
          <h2>Về chúng tôi</h2>
          <p>
            Chúng tôi cung cấp các sản phẩm tổ yến chất lượng cao, mang lại sức khỏe và giá trị tốt nhất
            cho khách hàng. Cam kết 100% sản phẩm tự nhiên, không chất bảo quản.
          </p>
        </div> */}
      </div>
    </MainLayout>
  );
};

export default HomePage;
