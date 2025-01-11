import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import MainLayout from '../../layouts/MainLayout';
import {
  fetchCategories,
  addCategory,
  modifyCategory,
  removeCategory,
} from '../../features/categories/categorySlice';
import '../../styles/AdminCategoryManage.scss';

const AdminCategoryManagement = () => {
  const dispatch = useDispatch();
  const { categories, loading, error } = useSelector((state) => state.categories);

  const [form, setForm] = useState({
    categoryName: '',
    description: '',
  });

  const [isEditing, setIsEditing] = useState(false);
  const [currentCategoryId, setCurrentCategoryId] = useState(null);

  useEffect(() => {
    dispatch(fetchCategories());
  }, [dispatch]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (isEditing && currentCategoryId !== null) {
      dispatch(modifyCategory({ id: currentCategoryId, ...form }));
    } else {
      dispatch(addCategory(form));
    }

    resetForm();
  };

  const resetForm = () => {
    setForm({
      categoryName: '',
      description: '',
    });
    setIsEditing(false);
    setCurrentCategoryId(null);
  };

  const handleEdit = (category) => {
    setForm({
      categoryName: category.categoryName,
      description: category.description,
    });
    setIsEditing(true);
    setCurrentCategoryId(category.categoryId);
  };

  const handleDelete = (id) => {
    if (window.confirm('Bạn có chắc chắn muốn xóa loại sản phẩm này?')) {
      dispatch(removeCategory(id));
    }
  };

  return (
    <MainLayout>
      <div className="admin-category-management">
        <h1>Quản lý loại sản phẩm</h1>

        <form className="category-form" onSubmit={handleSubmit}>
          <input
            type="text"
            name="categoryName"
            placeholder="Tên loại sản phẩm"
            value={form.categoryName}
            onChange={handleInputChange}
            required
          />

          <textarea
            name="description"
            placeholder="Mô tả"
            value={form.description}
            onChange={handleInputChange}
            required
          />

          <button type="submit">{isEditing ? 'Cập nhật' : 'Thêm loại sản phẩm'}</button>
        </form>

        {loading ? (
          <p>Loading...</p>
        ) : error ? (
          <p>Error: {error}</p>
        ) : (
          <table className="category-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Tên loại sản phẩm</th>
                <th>Mô tả</th>
                <th>Hành động</th>
              </tr>
            </thead>
            <tbody>
              {categories.map((category,index) => (
                <tr key={category.categoryId|| index}>
                  <td>{category.categoryId}</td>
                  <td>{category.categoryName}</td>
                  <td>{category.description}</td>
                  <td>
                    <button onClick={() => handleEdit(category)}>Sửa</button>
                    <button onClick={() => handleDelete(category.categoryId)}>Xóa</button>
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

export default AdminCategoryManagement;