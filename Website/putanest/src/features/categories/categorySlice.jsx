import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { getAllCategories, createCategory, updateCategory, deleteCategory } from '../../utils/API/category';

// Async action: Lấy danh sách danh mục
export const fetchCategories = createAsyncThunk(
  'categories/fetchCategories',
  async () => await getAllCategories()
);

// Async action: Tạo danh mục mới
export const addCategory = createAsyncThunk(
  'categories/addCategory',
  async ({ categoryName, description }) => await createCategory(categoryName, description)
);

// Async action: Cập nhật danh mục
export const modifyCategory = createAsyncThunk(
  'categories/modifyCategory',
  async ({ id, categoryName, description }) => await updateCategory(id, categoryName, description)
);

// Async action: Xóa danh mục
export const removeCategory = createAsyncThunk(
  'categories/removeCategory',
  async (id) => await deleteCategory(id)
);

const categorySlice = createSlice({
  name: 'categories',
  initialState: {
    categories: [],
    loading: false,
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchCategories.pending, (state) => {
        state.loading = true;
      })
      .addCase(fetchCategories.fulfilled, (state, action) => {
        state.loading = false;
        state.categories = action.payload;
      })
      .addCase(fetchCategories.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message;
      })
      .addCase(addCategory.pending, (state) => {
        state.loading = true;
      })
      .addCase(addCategory.fulfilled, (state, action) => {
        state.loading = false;
        state.categories.push(action.payload);
      })
      .addCase(addCategory.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message;
      })
      .addCase(modifyCategory.pending, (state) => {
        state.loading = true;
      })
      .addCase(modifyCategory.fulfilled, (state, action) => {
        state.loading = false;
        const index = state.categories.findIndex(category => category.id === action.payload.id);
        if (index !== -1) {
          state.categories[index] = action.payload;
        }
      })
      .addCase(modifyCategory.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message;
      })
      .addCase(removeCategory.pending, (state) => {
        state.loading = true;
      })
      .addCase(removeCategory.fulfilled, (state, action) => {
        state.loading = false;
        state.categories = state.categories.filter(category => category.id !== action.payload.id);
      })
      .addCase(removeCategory.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message;
      });
  },
});

export default categorySlice.reducer;