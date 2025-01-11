package com.ntq.putanest.service;

import com.ntq.putanest.pojo.Users;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UsersService {
    // Lấy danh sách tất cả người dùng
    List<Users> getAllUsers();

    // Lấy thông tin người dùng theo ID
    Optional<Users> getUserById(Integer userId);

    // Lấy thông tin người dùng theo Email
    Optional<Users> getUserByEmail(String email);

    // Thêm người dùng mới
    Users createUser(Users user, MultipartFile avatar) throws IOException;

    // Cập nhật thông tin người dùng
    Users updateUser(Integer userId, Users user, MultipartFile avatar) throws IOException;

    // Xóa người dùng
    void deleteUser(Integer userId);

    // Kiểm tra thông tin đăng nhập
    Optional<Map<String, String>> authenticate(String username, String password);

//    void logout(String username);
}

