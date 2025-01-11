import React from 'react';
import { BrowserRouter as Router, Route, Switch, Link } from 'react-router-dom';
import ProductPage from '../pages/Product/ProductPage';
import CategoryPage from '../pages/Category/CategoryPage';

const AdminLayout = () => {
  return (
    <Router>
      <div>
        <nav>
          <ul>
            <li><Link to="/admin/products">Quản lý sản phẩm</Link></li>
            <li><Link to="/admin/categories">Quản lý danh mục</Link></li>
          </ul>
        </nav>
        <Switch>
          <Route path="/admin/products" component={ProductPage} />
          <Route path="/admin/categories" component={CategoryPage} />
        </Switch>
      </div>
    </Router>
  );
};

export default AdminLayout;