import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { loginUser, createUser, logoutUser } from "../../features/users/userSlice";
import { useNavigate } from 'react-router-dom';
import '../../styles/Auth.scss';
const AuthComponent = () => {
  const dispatch = useDispatch();
  const { loading, error, currentUser } = useSelector((state) => state.users);
  const navigate = useNavigate();
  const [isRegister, setIsRegister] = useState(false);
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    fullName: "",
    email: "",
    phoneNumber: "",
    address: "",
    role: "",
    avatar: null,
  });

  useEffect(() => {
    if (currentUser) {
      navigate('/'); // Chuyển hướng về trang chủ sau khi đăng nhập thành công
    }
  }, [currentUser, navigate]);


  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleFileChange = (e) => {
    setFormData((prev) => ({ ...prev, avatar: e.target.files[0] }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!formData.username || !formData.password) {
      return;
    }
    if (isRegister) {
      const data = new FormData();
      data.append("email", formData.email);
      data.append("username", formData.username);
      data.append("password", formData.password);
      data.append("fullName", formData.fullName);
      data.append("phoneNumber", formData.phoneNumber);
      data.append("address", formData.address);
      data.append("role", formData.role);
      data.append("avatar", formData.avatar);

      if (formData.avatar) data.append("avatar", formData.avatar);
      dispatch(createUser(data));
    } else {
      dispatch(loginUser({ username: formData.username, password: formData.password }));
    }
  };

  const handleLogout = () => {
    dispatch(logoutUser());
  };

  return (
    <div className="auth-container">
      <h2>{isRegister ? "Register" : "Login"}</h2>

      {error && <p className="error-message">{error}</p>}

      <form onSubmit={handleSubmit}>
        {isRegister && (
          <div>
            <label>Họ và tên:</label>
            <input
              type="text"
              name="fullName"
              value={formData.fullName}
              onChange={handleInputChange}
              required
            />
          </div>
        )}

        <div>
          <label>Tên đăng nhập:</label>
          <input
            type="text"
            name="username"
            value={formData.username}
            onChange={handleInputChange}
            required
          />
        </div>

        <div>
          <label>Mật khẩu:</label>
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleInputChange}
            required
          />
        </div>

        {isRegister && (
          <div>
            <label>Email:</label>
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleInputChange}
              required
            />
          </div>
        )}
        {isRegister && (
          <div>
            <label>Địa chỉ:</label>
            <input
              type="text"
              name="address"
              value={formData.address}
              onChange={handleInputChange}
              required
            />
          </div>
        )}

        {isRegister && (
          <div>
            <label>Số điện thoại:</label>
            <input
              type="number"
              name="phoneNumber"
              value={formData.phoneNumber}
              onChange={handleInputChange}
              required
            />
          </div>
        )}

        {isRegister && (
          <div>
            <label>Role:</label>
            <input
              type="text"
              name="role"
              value={formData.role}
              onChange={handleInputChange}
              required
            />
          </div>
        )}

        {isRegister && (
          <div>
            <label>Avatar:</label>
            <input type="file" name="avatar" onChange={handleFileChange} />
          </div>
        )}

        <button type="submit" disabled={loading}>
          {loading ? "Processing..." : isRegister ? "Register" : "Login"}
        </button>
      </form>

      <p>
        {isRegister ? "Already have an account?" : "Don't have an account?"} &nbsp;
        <button onClick={() => setIsRegister(!isRegister)}>
          {isRegister ? "Login here" : "Register here"}
        </button>
      </p>

      {/* {currentUser && (
        <div>
          <h3>Welcome, {currentUser.username || currentUser.email}!</h3>
        </div>
      )} */}
    </div>
  );
};

export default AuthComponent;
