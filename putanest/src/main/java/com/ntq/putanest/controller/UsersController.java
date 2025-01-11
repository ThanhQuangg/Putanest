package com.ntq.putanest.controller;

import com.ntq.putanest.dto.UsersDTO;
import com.ntq.putanest.pojo.Users;
import com.ntq.putanest.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public List<Users> getAllUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable("id") Integer id) {
        Optional<Users> user = usersService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Users> createUser(@ModelAttribute UsersDTO userDTO, @RequestParam("avatar") MultipartFile avatar) throws IOException {
        Users user = new Users();
        // Chuyển đổi từ DTO sang entity
        user.setUsername(userDTO.getUsername());
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAddress(userDTO.getAddress());
        user.setRole(userDTO.getRole());
        if (!avatar.isEmpty()) {
            String avatarFileName = avatar.getOriginalFilename();
            user.setAvatar(avatarFileName);
            // Lưu tệp avatar vào thư mục hoặc cơ sở dữ liệu
        }
        Users createdUser = usersService.createUser(user, avatar);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Users> updateUser(@PathVariable("id") Integer id, @ModelAttribute UsersDTO userDTO, @RequestParam("avatar") MultipartFile avatar) throws IOException {
        try {
            Users user = new Users();
            // Chuyển đổi từ DTO sang entity
            user.setFullName(userDTO.getFullName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setAddress(userDTO.getAddress());
            user.setRole(userDTO.getRole());
            Users updatedUser = usersService.updateUser(id, user, avatar);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
        try {
            usersService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody UsersDTO loginDTO) {
        try {
            // Kiểm tra username và password
            Optional<Map<String, String>> authResponse = usersService.authenticate(loginDTO.getUsername(), loginDTO.getPassword());
            if (authResponse.isPresent()) {
                Map<String, String> response = authResponse.get();
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                        "message", "Tên đăng nhập hoặc mật khẩu không đúng"
                ));
            }
        } catch (Exception e) {
            // Xử lý lỗi hệ thống
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Lỗi hệ thống",
                    "error", e.getMessage()
            ));
        }
    }


}
