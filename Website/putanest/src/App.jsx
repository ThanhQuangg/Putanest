import React from 'react';
import { Routes, Route } from 'react-router-dom';
import HomePage from './pages/Home/HomePage';
import ProductList from './pages/Product/ProductList';
import ProductDetail from './pages/Product/ProductDetail';
import CartPage from './pages/Cart/CartPage';
import MainLayout from './layouts/MainLayout';
import AppRoutes from './routes/routes';
import { ToastContainer } from "react-toastify";

function App() {
  return (
    <div>
       <ToastContainer />
      <AppRoutes /> 
    </div>
  );
}

export default App;