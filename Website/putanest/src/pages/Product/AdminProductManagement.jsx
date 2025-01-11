// import React, { useState, useEffect } from 'react';
// import { useDispatch, useSelector } from 'react-redux';
// import MainLayout from '../../layouts/MainLayout';
// import {
//   fetchProducts,
//   fetchCategories, // Thêm action này
//   createProduct,
//   updateProduct,
//   deleteProduct,
// } from '../../features/products/productSlice';
// import '../../styles/AdminProductManage.scss';

// const AdminProductManagement = () => {
//   const dispatch = useDispatch();
//   const { products, categories, loading } = useSelector((state) => state.products);

//   const [form, setForm] = useState({
//     productName: '',
//     categoryId: '',
//     price: '',
//     quantity: '',
//     description: '',
//     avatar: null,
//   });
//   const [isEditing, setIsEditing] = useState(false);
//   const [currentProductId, setCurrentProductId] = useState(null);

//   // Lấy sản phẩm và danh mục khi component được render
//   useEffect(() => {
//     dispatch(fetchProducts());
//     dispatch(fetchCategories());
//   }, [dispatch]);

//   const handleInputChange = (e) => {
//     const { name, value } = e.target;
//     setForm({ ...form, [name]: value });
//   };

//   const handleFileChange = (e) => {
//     setForm({ ...form, avatar: e.target.files[0] });
//   };

//   const handleSubmit = (e) => {
//     e.preventDefault();

//     // Tạo FormData
//     const formData = new FormData();
//     formData.append('productName', form.productName);
//     formData.append('categoryId', form.categoryId);
//     formData.append('price', form.price);
//     formData.append('quantity', form.quantity);
//     formData.append('description', form.description);
//     if (form.avatar) {
//       formData.append('avatar', form.avatar); // Chỉ thêm avatar nếu tồn tại
//     }
//     if (isEditing) {
//       dispatch(updateProduct({ id: currentProductId, product: formData }));
//     } else {
//       dispatch(createProduct(formData));
//     }
//     // Reset form
//     setForm({
//       productName: '',
//       categoryId: '',
//       price: '',
//       quantity: '',
//       description: '',
//       avatar: null,
//     });
//     setIsEditing(false);
//     setCurrentProductId(null);
//   };

//   const handleEdit = (product) => {
//     setForm({
//       productName: product.productName,
//       categoryId: product.categoryId,
//       price: product.price,
//       quantity: product.quantity,
//       description: product.description,
//       avatar: product.avatar,
//     });
//     setIsEditing(true);
//     setCurrentProductId(product.productId);
//   };

//   const handleDelete = (id) => {
//     if (window.confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')) {
//       dispatch(deleteProduct(id));
//     }
//   };

//   return (
//     <MainLayout>
//       <div className="admin-product-management">
//         <h1>Quản lý sản phẩm</h1>
//         <form className="product-form" onSubmit={handleSubmit}>
//           <input
//             type="text"
//             name="productName"
//             placeholder="Tên sản phẩm"
//             value={form.productName}
//             onChange={handleInputChange}
//             required
//           />

//           <select
//             name="categoryId"
//             value={form.categoryId}
//             onChange={handleInputChange}
//             required
//           >
//             <option value="">Chọn loại sản phẩm</option>
//             {categories.map((category) => (
//               <option key={category.categoryId} value={category.categoryId}>
//                 {category.categoryName}
//               </option>
//             ))}
//           </select>

//           <input
//             type="number"
//             name="price"
//             placeholder="Giá"
//             value={form.price}
//             onChange={handleInputChange}
//             required
//           />
//           <input
//             type="number"
//             name="quantity"
//             placeholder="Số lượng"
//             value={form.quantity}
//             onChange={handleInputChange}
//             required
//           />
//           <textarea
//             name="description"
//             placeholder="Mô tả sản phẩm"
//             value={form.description}
//             onChange={handleInputChange}
//             required
//           />
//           <input type="file" name="avatar" onChange={handleFileChange} />
//           <button type="submit">{isEditing ? 'Cập nhật' : 'Thêm sản phẩm'}</button>
//         </form>

//         {loading ? (
//           <p>Loading...</p>
//         ) : (
//           <table className="product-table">
//             <thead>
//               <tr>
//                 <th>ID</th>
//                 <th>Tên sản phẩm</th>
//                 <th>Giá</th>
//                 <th>Số lượng</th>
//                 <th>Loại sản phẩm</th>
//                 <th>Hành động</th>
//               </tr>
//             </thead>
//             <tbody>
//               {products.map((product) => (
//                 <tr key={product.productId}>
//                   <td>{product.productId}</td>
//                   <td>{product.productName}</td>
//                   <td>{product.price}</td>
//                   <td>{product.quantity}</td>
//                   <td>
//                     {
//                       categories.find((c) => c.id === product.categoryId)?.categoryName ||
//                       'Không xác định'
//                     }
//                   </td>
//                   <td>
//                     <button onClick={() => handleEdit(product)}>Sửa</button>
//                     <button onClick={() => handleDelete(product.productId)}>Xóa</button>
//                   </td>
//                 </tr>
//               ))}
//             </tbody>
//           </table>
//         )}
//       </div>
//     </MainLayout>
//   );
// };

// export default AdminProductManagement;




import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import MainLayout from '../../layouts/MainLayout';
import {
  fetchProducts,
  fetchCategories,
  createProduct,
  updateProduct,
  deleteProduct,
} from '../../features/products/productSlice';
import '../../styles/AdminProductManage.scss';

const AdminProductManagement = () => {
  const dispatch = useDispatch();
  const { products, categories, loading } = useSelector((state) => state.products);

  const [form, setForm] = useState({
    productName: '',
    categoryId: '',
    price: '',
    quantity: '',
    description: '',
    avatar: null,
  });

  const [isEditing, setIsEditing] = useState(false);
  const [currentProductId, setCurrentProductId] = useState(null);

  useEffect(() => {
    dispatch(fetchProducts());
    dispatch(fetchCategories());
  }, [dispatch]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  };

  const handleFileChange = (e) => {
    setForm({ ...form, avatar: e.target.files[0] });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    
    const formData = new FormData();
    formData.append('product', new Blob([JSON.stringify({
      productName: form.productName,
      categoryId: form.categoryId,
      price: form.price,
      quantity: form.quantity,
      description: form.description,
    })], { type: "application/json" }));
  
    if (form.avatar) {
      formData.append('avatar', form.avatar);
    }
  
    if (isEditing) {
      dispatch(updateProduct({ id: currentProductId, product: formData }));
    } else {
      dispatch(createProduct(formData));
    }
  
    resetForm();
  };

  const handleEdit = (product) => {
    setForm({
      productName: product.productName,
      categoryId: product.categoryId,
      price: product.price,
      quantity: product.quantity,
      description: product.description,
    });
    setIsEditing(true);
    setCurrentProductId(product.productId);
  };

  const resetForm = () => {
    setForm({
      productName: '',
      categoryId: '',
      price: '',
      quantity: '',
      description: '',
      avatar: null,
    });
    setIsEditing(false);
    setCurrentProductId(null);
  };

  

  const handleDelete = (id) => {
    if (window.confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')) {
      dispatch(deleteProduct(id));
    }
  };

  return (
    <MainLayout>
      <div className="admin-product-management">
        <h1>Quản lý sản phẩm</h1>
        <form className="product-form" onSubmit={handleSubmit}>
          <input
            type="text"
            name="productName"
            placeholder="Tên sản phẩm"
            value={form.productName}
            onChange={handleInputChange}
            required
          />

          <select
            name="categoryId"
            value={form.categoryId}
            onChange={handleInputChange}
            required
          >
            <option value="">Chọn loại sản phẩm</option>
            {categories.map((category) => (
              <option key={category.categoryId} value={category.categoryId}>
                {category.categoryName}
              </option>
            ))}
          </select>

          <input
            type="number"
            name="price"
            placeholder="Giá"
            value={form.price}
            onChange={handleInputChange}
            required
          />

          <input
            type="number"
            name="quantity"
            placeholder="Số lượng"
            value={form.quantity}
            onChange={handleInputChange}
            required
          />

          <textarea
            name="description"
            placeholder="Mô tả sản phẩm"
            value={form.description}
            onChange={handleInputChange}
            required
          />

          <input type="file" name="avatar" onChange={handleFileChange} />
          <button type="submit">{isEditing ? 'Cập nhật' : 'Thêm sản phẩm'}</button>
        </form>

        {loading ? (
          <p>Loading...</p>
        ) : (
          <table className="product-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Tên sản phẩm</th>
                <th>Giá</th>
                <th>Số lượng</th>
                <th>Loại sản phẩm</th>
                <th>Hành động</th>
              </tr>
            </thead>
            <tbody>
              {products.map((product) => (
                <tr key={product.productId}>
                  <td>{product.productId}</td>
                  <td>{product.productName}</td>
                  <td>{product.price.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })}</td>
                  <td>{product.quantity}</td>
                  <td>{
                    categories.find((c) => c.categoryId === product.categoryId)?.categoryName ||
                    'Không xác định'
                  }</td>
                  <td>
                    <button onClick={() => handleEdit(product)}>Sửa</button>
                    <button onClick={() => handleDelete(product.productId)}>Xóa</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </MainLayout>
  );
};

export default AdminProductManagement;
