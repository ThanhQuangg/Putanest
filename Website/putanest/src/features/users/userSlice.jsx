import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { getAllUsers, createUser as apiCreateUser, login } from "../../utils/API/userApi";

const saveToLocalStorage = (key, value) => localStorage.setItem(key, value);
const removeFromLocalStorage = (key) => localStorage.removeItem(key);

// Async thunk để lấy danh sách người dùng
export const fetchUsers = createAsyncThunk("users/fetchUsers", async (_, thunkAPI) => {
  try {
    const users = await getAllUsers();
    return users;
  } catch (error) {
    return thunkAPI.rejectWithValue(error.response.data);
  }
});

// Async thunk để tạo người dùng mới
export const createUser = createAsyncThunk(
  'users/createUser',
  async (user) => {
    const response = await apiCreateUser(user);
    return response;
  }
);

// Async thunk để xử lý đăng nhập
export const loginUser = createAsyncThunk(
  "users/loginUser",
  async ({ username, password }, thunkAPI) => {
    try {
      const response = await login(username, password); // Gọi API
      const { token, userId, username: responseUsername } = response; // Lấy dữ liệu từ phản hồi

      // Lưu thông tin vào localStorage
      saveToLocalStorage("token", token);
      saveToLocalStorage("username", responseUsername);
      saveToLocalStorage("userId", userId);
      localStorage.setItem('isLoggedIn', 'true');
      return { token, userId, username: responseUsername }; // Trả dữ liệu
    } catch (error) {
      const errorMessage = error.response?.data || error.message || "Failed to log in";
      return thunkAPI.rejectWithValue(errorMessage);
    }
  }
);

// Slice quản lý state người dùng
const userSlice = createSlice({
  name: "users",
  initialState: {
    userList: [],
    currentUser: null,
    loading: false,
    error: null,
  },
  reducers: {
    logoutUser: (state) => {
      state.currentUser = null; // Xóa thông tin người dùng hiện tại
      removeFromLocalStorage('token');
      removeFromLocalStorage('username');
      removeFromLocalStorage('userId');
      removeFromLocalStorage('user');
      localStorage.setItem('isLoggedIn', 'false');
      
    },
  },
  extraReducers: (builder) => {
    builder
      // Xử lý fetchUsers
      .addCase(fetchUsers.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchUsers.fulfilled, (state, action) => {
        state.loading = false;
        state.userList = action.payload;
      })
      .addCase(fetchUsers.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload || "Failed to fetch users";
      })

      // Xử lý createUser
      .addCase(createUser.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(createUser.fulfilled, (state, action) => {
        state.loading = false;
        state.userList.push(action.payload); // Thêm người dùng mới vào danh sách
      })
      .addCase(createUser.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload || "Failed to create user";
      })

      // Xử lý loginUser
      .addCase(loginUser.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(loginUser.fulfilled, (state, action) => {
        state.loading = false;
        state.currentUser = action.payload; // Lưu thông tin user trả về từ API
      })
      .addCase(loginUser.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload || "Failed to log in";
      });
  },
});

export const { logoutUser } = userSlice.actions;
export default userSlice.reducer;
