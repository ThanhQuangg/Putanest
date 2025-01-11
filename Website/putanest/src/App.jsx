import React from 'react';
import { Routes, Route } from 'react-router-dom';
import HomePage from './pages/Home/HomePage';
import ProductList from './pages/Product/ProductList';
import ProductDetail from './pages/Product/ProductDetail';
import CartPage from './pages/Cart/CartPage';
import MainLayout from './layouts/MainLayout';
import AppRoutes from './routes/routes';

// function App() {
//   return (
//     <Routes>
//       {/* <Route element={<MainLayout />}> */}
//         <Route path="/" element={<HomePage />} />
//         <Route path="/products" element={<ProductList />} />
//         <Route path="/products/:id" element={<ProductDetail />} />
//         <Route path="/cart" element={<CartPage />} />
//       {/* </Route> */}
//     </Routes>
//   );
// }

// export default App;

function App() {
  return (
    <div>
      <AppRoutes /> {/* Sử dụng AppRoutes */}
    </div>
  );
}

export default App;