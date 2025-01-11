import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchProducts } from '../../features/products/productSlice';
import { useNavigate } from 'react-router-dom';
import '../../styles/ProductList.scss';
import MainLayout from '../../layouts/MainLayout';

const ProductList = () => {
    const dispatch = useDispatch();
    const { products, loading, error } = useSelector((state) => state.products);
    const navigate = useNavigate();

    useEffect(() => {
        dispatch(fetchProducts());
    }, [dispatch]);

    const handleProductClick = (productId) => {
        navigate(`/products/${productId}`);
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <MainLayout>
            <div className="content">
                <div className="products">
                    {products.map((product) => (
                        <div
                            key={product.id}
                            className="product-card"
                            onClick={() => handleProductClick(product.productId)}
                        >
                            <img
                                src={product.avatar || '/assets/image/avatar.png'}
                                alt={product.productName}
                            />
                            <h5>{product.productName}</h5>
                            <p>Price: {product.price.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })}</p>
                            {/* <button onClick={() => handleProductClick(product.productId)}>Xem chi tiết</button>                             */}
                            <button style={{marginLeft:"10px"}}>Thêm vào giỏ hàng</button>
                        </div>
                    ))}
                </div>
            </div>
        </MainLayout>
    );
};

export default ProductList;