import React from "react";
import '../../styles/Footer.scss';
const Footer = () => (
  <footer className="footer bg-dark text-white py-5">
    <div className="container">
      <div className="row">
        <div className="col-md-4">
          <h5 className="footer-title">Yến Sào</h5>
          <p className="footer-description">
            Chuyên cung cấp các sản phẩm yến sào chất lượng, đảm bảo sức khỏe
            và giá trị dinh dưỡng cho khách hàng.
          </p>
        </div>
        <div className="col-md-4">
          <h5 className="footer-title">Liên Kết Nhanh</h5>
          <ul className="footer-links list-unstyled">
            <li style={{textDecoration:""}}><a href="#" className="text-white text-decoration-none">Trang Chủ</a></li>
            <li><a href="#" className="text-white text-decoration-none">Sản Phẩm</a></li>
            <li><a href="#" className="text-white text-decoration-none">Giỏ Hàng</a></li>
            <li><a href="#" className="text-white text-decoration-none">Liên Hệ</a></li>
          </ul>
          <div className="text-center mt-4" >
            <p className="mb-0">&copy; 2024 Yến Sào. All rights reserved.</p>
          </div>
        </div>
        <div className="col-md-4">
          <h5 className="footer-title">Liên Hệ</h5>
          <p><i className="bi bi-telephone"></i> Hotline: 0123 456 789</p>
          <p><i className="bi bi-envelope"></i> Email: info@yensao.com</p>
          <p><i className="bi bi-geo-alt"></i> Địa chỉ: 427 đường Dương Thị Mười</p>
        </div>
      </div>

    </div>
  </footer>
);

export default Footer;
