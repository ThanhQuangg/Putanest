package com.ntq.putanest.serviceimpl;

import com.ntq.putanest.dto.CartsDTO;
import com.ntq.putanest.pojo.Carts;
import com.ntq.putanest.pojo.Users;
import com.ntq.putanest.repository.UsersRepository;
import com.ntq.putanest.security.JwtTokenProvider;
import com.ntq.putanest.service.CartsService;
import com.ntq.putanest.service.CloudinaryService;
import com.ntq.putanest.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CartsService cartsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public Optional<Users> getUserById(Integer userId) {
        return usersRepository.findById(userId);
    }

    @Override
    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    @Override
    public Users createUser(Users user, MultipartFile avatar) throws IOException {
        if (avatar != null && !avatar.isEmpty()) {
            try {
                String avatarUrl = cloudinaryService.uploadImage(avatar);
                user.setAvatar(avatarUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload avatar", e);
            }
        }
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Mã hóa mật khẩu
        Users createdUser = usersRepository.save(user);

        // Tự động tạo Cart cho người dùng
        Carts cart = new Carts();
        cart.setUserId(createdUser.getUserId());
        cart.setCreatedAt(LocalDateTime.now());
        CartsDTO cartDTO = new CartsDTO();
        cartDTO.setUserId(cart.getUserId());
        cartDTO.setCreatedAt(cart.getCreatedAt());
        cartsService.createCart(cartDTO);

        return createdUser;
    }

    @Override
    public Users updateUser(Integer userId, Users user, MultipartFile avatar) throws IOException {
        if (usersRepository.existsById(userId)) {
            if (avatar != null && !avatar.isEmpty()) {
                try {
                    String avatarUrl = cloudinaryService.uploadImage(avatar);
                    user.setAvatar(avatarUrl);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to upload avatar", e);
                }
            }
            user.setUserId(userId);
            user.setUpdatedAt(LocalDateTime.now());
            return usersRepository.save(user);
        }
        throw new IllegalArgumentException("User ID không tồn tại.");
    }

    @Override
    public void deleteUser(Integer userId) {
        if (usersRepository.existsById(userId)) {
            usersRepository.deleteById(userId);
        } else {
            throw new IllegalArgumentException("User ID không tồn tại.");
        }
    }

    @Override
    public Optional<Map<String, String>> authenticate(String username, String password) {
        Optional<Users> user = usersRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            // Tạo đối tượng Authentication từ thông tin người dùng
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user.get().getUsername(), null, Collections.emptyList()
            );

            // Tạo token từ đối tượng Authentication
            String token = jwtTokenProvider.generateToken(authentication, user.get().getUserId());

            // Tạo response chứa token và username
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("username", user.get().getUsername());
            response.put("userId", user.get().getUserId().toString());
            return Optional.of(response);
        }
        return Optional.empty();
    }
}

