import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { getAllProducts, getProductById, createProduct as apiCreateProduct, getProductsByCategory, updateProduct as apiUpdateProduct, deleteProduct as apiDeleteProduct } from '../../utils/API/product';
import { getAllCategories } from '../../utils/API/category';
// Thunk để fetch danh sách sản phẩm
export const fetchProducts = createAsyncThunk(
    'products/fetchProducts',
    async () => {
        const response = await getAllProducts();
        return response;
    }
);

// Thunk để fetch chi tiết sản phẩm
export const fetchProductDetail = createAsyncThunk(
    'products/fetchProductDetail',
    async (id) => {
        const response = await getProductById(id);
        return response;
    }
);

// Thunk để tạo sản phẩm mới
export const createProduct = createAsyncThunk(
    'products/createProduct',
    async (formData, { rejectWithValue }) => {
        try {
            const response = await apiCreateProduct(formData);
            return response;
        } catch (error) {
            return rejectWithValue(error.response?.data || error.message);
        }
    }
);

// Thunk để cập nhật sản phẩm
export const updateProduct = createAsyncThunk(
    'products/updateProduct',
    async ({ id, formData }, { rejectWithValue }) => {
        try {
            const response = await apiUpdateProduct(id, formData);
            return response;
        } catch (error) {
            return rejectWithValue(error.response?.data || error.message);
        }
    }
);

// Thunk để xóa sản phẩm
export const deleteProduct = createAsyncThunk(
    'products/deleteProduct',
    async (id) => {
        await apiDeleteProduct(id);
        return id;
    }
);

export const fetchCategories = createAsyncThunk(
    'products/fetchCategories',
    async () => {
      const response = await getAllCategories();
      return response;
    }
  );

// Tạo slice
const productSlice = createSlice({
    name: 'products',
    initialState: {
        products: [],
        categories: [],
        selectedProduct: null,
        loading: false,
        error: null,
    },
    reducers: {},
    extraReducers: (builder) => {
        builder
            // Xử lý trạng thái fetchProducts
            .addCase(fetchProducts.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(fetchProducts.fulfilled, (state, action) => {
                state.loading = false;
                state.products = action.payload;
            })
            .addCase(fetchProducts.rejected, (state, action) => {
                state.loading = false;
                state.error = action.error.message;
            })
            // Xử lý trạng thái fetchProductDetail
            .addCase(fetchProductDetail.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(fetchProductDetail.fulfilled, (state, action) => {
                state.loading = false;
                state.selectedProduct = action.payload;
            })
            .addCase(fetchProductDetail.rejected, (state, action) => {
                state.loading = false;
                state.error = action.error.message;
            })
            // Xử lý trạng thái createProduct
            .addCase(createProduct.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(createProduct.fulfilled, (state, action) => {
                state.loading = false;
                state.products.push(action.payload);
            })
            .addCase(createProduct.rejected, (state, action) => {
                state.loading = false;
                state.error = action.error.message;
            })
            // Xử lý trạng thái updateProduct
            .addCase(updateProduct.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(updateProduct.fulfilled, (state, action) => {
                state.loading = false;
                const index = state.products.findIndex(product => product.productId === action.payload.id);
                if (index !== -1) {
                    state.products[index] = action.payload;
                }
            })
            .addCase(updateProduct.rejected, (state, action) => {
                state.loading = false;
                state.error = action.error.message;
            })
            // Xử lý trạng thái deleteProduct
            .addCase(deleteProduct.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(deleteProduct.fulfilled, (state, action) => {
                state.loading = false;
                state.products = state.products.filter(product => product.productId !== action.payload);
            })
            .addCase(deleteProduct.rejected, (state, action) => {
                state.loading = false;
                state.error = action.error.message;
            })
            .addCase(fetchCategories.pending, (state) => {
                state.loading = true;
                state.error = null;
              })
              .addCase(fetchCategories.fulfilled, (state, action) => {
                state.loading = false;
                state.categories = action.payload;
              })
              .addCase(fetchCategories.rejected, (state, action) => {
                state.loading = false;
                state.error = action.error.message;
              });
    },
});

export default productSlice.reducer;