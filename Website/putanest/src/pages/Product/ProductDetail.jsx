import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchProductDetail } from '../../features/products/productSlice';
import { addToCart } from '../../features/cart/cartSlice';
import { useParams } from 'react-router-dom';
import '../../styles/ProductDetail.scss';
import MainLayout from '../../layouts/MainLayout';

const ProductDetail = () => {
    const { id } = useParams();
    const dispatch = useDispatch();
    const { selectedProduct, loading, error } = useSelector((state) => state.products);
    const [quantity, setQuantity] = useState(1);
    const [isImageModalOpen, setImageModalOpen] = useState(false);

    useEffect(() => {
        dispatch(fetchProductDetail(id));
    }, [dispatch, id]);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    if (!selectedProduct) {
        return <div>Product not found.</div>;
    }

    const handleImageClick = () => {
        setImageModalOpen(true);
    };

    const handleCloseModal = () => {
        setImageModalOpen(false);
    };

    const handleAddToCart = () => {
        dispatch(addToCart({ 
            productId: selectedProduct.id, 
            productName: selectedProduct.productName, 
            price: selectedProduct.price, 
            avatar: selectedProduct.avatar, 
            quantity 
        }));
    };

    return (
        <MainLayout>
            <div className="product-detail">
                <h1>{selectedProduct.productName}</h1>
                <img
                    className="product-image"
                    src={selectedProduct.avatar || '/assets/image/avatar.png'}
                    alt={selectedProduct.productName}
                    onClick={handleImageClick}
                />
                <p className="product-price">Giá: {selectedProduct.price.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })}</p>
                <p className="product-description">Chi tiết sản phẩm: {selectedProduct.description}</p>
                <p className="product-category">Loại sản phẩm: {selectedProduct.categoryName}</p>
                <p>Số lượng</p>
                <div>
                    <input 
                        type="number" 
                        min="1" 
                        value={quantity} 
                        onChange={(e) => setQuantity(Number(e.target.value))} 
                        style={{ width: '60px', marginRight: '10px' }}
                    />
                    <button onClick={handleAddToCart}>Thêm vào giỏ hàng</button>
                </div>

                {/* Modal for Image */}
                {isImageModalOpen && (
                    <div className="image-modal" onClick={handleCloseModal}>
                        <img
                            src={selectedProduct.avatar || '/assets/image/avatar.png'}
                            alt="Phóng to sản phẩm"
                            className="modal-image"
                        />
                    </div>
                )}
            </div>
        </MainLayout>
    );
};

export default ProductDetail;
