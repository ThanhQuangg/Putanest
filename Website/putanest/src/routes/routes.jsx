import React from 'react';
import { Routes, Route } from 'react-router-dom';
import HomePage from '../pages/Home/HomePage';
import ProductPage from '../pages/Product/ProductPage';
import MainLayout from '../layouts/MainLayout';
import AdminLayout from '../layouts/AdminLayout';
import ProductList from '../pages/Product/ProductList';
import ProductDetail from '../pages/Product/ProductDetail';
import AdminProductManager from '../pages/Product/AdminProductManagement';
import CategoryPage from '../pages/Category/CategoryPage';
import AdminCategoryManage from '../pages/Category/AdminCategoryManage';
import SearchResultsPage from '../pages/Product/SearchResultsPage';
import AuthComponent from '../pages/User/Auth';
import CartPage from '../pages/Cart/CartPage';
import UserOrdersPage from '../pages/Order/OrderPage';

const AppRoutes = () => {
  return (
    <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/products" element={<ProductPage />} />
        <Route path="/products/list" element={<ProductList />} />
        <Route path="/products/:id" element={<ProductDetail />} />
        <Route path="/category/:categoryId" element={<CategoryPage />} />
        <Route path="/admin/products" element={<AdminProductManager />} />
        <Route path="/admin/categories" element={<AdminCategoryManage />} />
        <Route path="/search-results" element={<SearchResultsPage />} />
        <Route path="/login" element={<AuthComponent />} />
        <Route path="/cart" element={<CartPage/>} />
        <Route path="/order" element={<UserOrdersPage/>} />
    </Routes> 
  );
};

export default AppRoutes;