import React from 'react';
import { Outlet, Link } from 'react-router-dom';

const AdminLayout = () => {
  return (
    <div>
      <nav>
        <ul>
          <li><Link to="/admin/products">Quản lý sản phẩm</Link></li>
          <li><Link to="/admin/categories">Quản lý danh mục</Link></li>
        </ul>
      </nav>
      <Outlet /> {/* Để hiển thị các trang con */}
    </div>
  );
};

export default AdminLayout;