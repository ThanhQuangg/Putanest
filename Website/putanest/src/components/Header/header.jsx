import React, { useState, useEffect } from "react";
import '../../styles/Header.scss';
import logo from "../../assets/image/logo.png";
import { getAllCategories } from "../../utils/API/category";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { logoutUser } from "../../features/users/userSlice";
import axios from 'axios';

const Header = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [categories, setCategories] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [username, setUsername] = useState("");
  const navigate = useNavigate();
  const dispatch = useDispatch();
  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const data = await getAllCategories();
        setCategories(data);
      } catch (error) {
        console.error("Lỗi khi lấy danh mục:", error);
      }
    };

    fetchCategories();

    // Kiểm tra xem người dùng đã đăng nhập chưa
    const isLoggedIn = localStorage.getItem("isLoggedIn") === "true";
    const storedUserId = localStorage.getItem("userId");
    const storedUsername = localStorage.getItem("username");

    if (isLoggedIn && storedUserId && storedUsername) {
      setIsLoggedIn(true);
      setUsername(storedUsername);
    }
  }, []);


  const handleLogin = () => {
    navigate(`/login`, { state: { isRegister: false } });
  };

  const handleRegister = () => {
    navigate(`/login`, { state: { isRegister: true } });
  };

  const handleLogout = () => {
    dispatch(logoutUser());
    setIsLoggedIn(false);
    setUsername("");
    navigate('/');
  };

  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value);
  };

  const handleSearch = async () => {
    if (searchQuery.trim()) {
      try {
        const response = await axios.get(`http://localhost:8080/api/products/search?q=${encodeURIComponent(searchQuery)}`);

        if (Array.isArray(response.data)) {
          const searchResults = response.data;
          navigate(`/search-results`, { state: { searchResults } });
        } else {
          console.error("Phản hồi API không phải là mảng:", response.data);
        }
      } catch (error) {
        console.error("Lỗi khi gọi API tìm kiếm:", error);
        alert("Đã xảy ra lỗi khi tìm kiếm. Vui lòng thử lại sau.");
      }
    } else {
      alert("Vui lòng nhập từ khóa tìm kiếm.");
    }
  };

  const handleSearchKeyDown = (e) => {
    if (e.key === "Enter") {
      handleSearch();
    }
  };

  const handleCategoryClick = (categoryId) => {
    navigate(`/category/${categoryId}`);
  };

  const handleNewOptionClick = () => {
    navigate('/products/list');
  };

  return (
    <header className="custom-header">
      <nav className="navbar navbar-expand-lg">
        <div className="container d-flex align-items-center justify-content-between">
          {/* Logo */}
          <div className="d-flex align-items-center">
            <a className="navbar-brand d-flex align-items-center" href="/">
              {/* <img src={logo} alt="Logo" className="brand-logo" /> */}
              <span>Yến Sào Liên Sự</span>
            </a>
          </div>

          {/* Menu */}
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav">
              <li className="nav-item">
                <a className="nav-link active" href="/">Trang chủ</a>
              </li>

              {/* Dropdown Loại sản phẩm */}
              <li className="nav-item dropdown">
                <a
                  className="nav-link dropdown-toggle"
                  id="categoriesDropdown"
                  href="/"
                  onClick={(e) => e.preventDefault()}
                  data-bs-toggle="dropdown"
                >
                  Loại sản phẩm
                </a>
                <ul className="dropdown-menu" id="categoriesDropdownMenu">
                  {categories.map((category) => (
                    <li key={category.categoryId}>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={() => handleCategoryClick(category.categoryId)}
                      >
                        {category.categoryName}
                      </a>
                    </li>
                  ))}
                  <li>
                    <a
                      className="dropdown-item"
                      href="#"
                      onClick={handleNewOptionClick}
                    >
                      Tất cả sản phẩm
                    </a>
                  </li>
                </ul>
              </li>

              <li className="nav-item">
                <a className="nav-link" href="/cart">Giỏ Hàng</a>
              </li>
              <li className="nav-item">
                <a className="nav-link" href="#">Liên Hệ</a>
              </li>

              {/* Dropdown Admin */}
              <li className="nav-item dropdown">
                <a
                  className="nav-link dropdown-toggle"
                  id="adminDropdown"
                  href="/"
                  onClick={(e) => e.preventDefault()}
                  data-bs-toggle="dropdown"
                >
                  Admin
                </a>
                <ul className="dropdown-menu">
                  <li>
                    <a className="dropdown-item" href="/admin/products">Quản lý sản phẩm</a>
                  </li>
                  <li>
                    <a className="dropdown-item" href="/admin/categories">Quản lý danh mục</a>
                  </li>
                </ul>
              </li>
            </ul>
          </div>

          {/* Tìm kiếm, đăng nhập/đăng xuất */}
          <div className="navbar-actions d-flex align-items-center">

            <div className="search-bar">
              <input
                type="text"
                placeholder="Tìm kiếm..."
                className="search-input"
                value={searchQuery}
                onChange={handleSearchChange}
                onKeyDown={handleSearchKeyDown}
              />
            </div>
            <div className="auth-actions">
              {isLoggedIn ? (
                <>
                  <p className="text">Welcome, {username}!</p>
                  <button className="btn" onClick={handleLogout}>Đăng xuất</button>
                </>
              ) : (
                <button className="btn" onClick={handleLogin}>Đăng nhập</button>
              )}
            </div>
          </div>
        </div>
      </nav>
    </header>
  );
};

export default Header;
