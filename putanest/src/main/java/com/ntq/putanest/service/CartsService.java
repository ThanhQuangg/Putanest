package com.ntq.putanest.service;

import com.ntq.putanest.dto.CartsDTO;

import java.util.List;
import java.util.Optional;

public interface CartsService {
    // Lấy danh sách tất cả giỏ hàng
    List<CartsDTO> getAllCarts();

    // Lấy giỏ hàng theo CartID
    Optional<CartsDTO> getCartById(Integer cartId);

    // Lấy giỏ hàng theo UserID
    Optional<CartsDTO> getCartByUserId(Integer userId);

    // Tạo giỏ hàng
    CartsDTO createCart(CartsDTO cartDTO);

    // Cập nhật trạng thái hoặc thông tin giỏ hàng
    CartsDTO updateCart(Integer cartId, CartsDTO cartDTO);

    // Xóa giỏ hàng theo CartID
    void deleteCart(Integer cartId);
}



//    package com.ntq.putanest.service;
//
//    import com.ntq.putanest.pojo.Carts;
//    import com.ntq.putanest.pojo.Orders;
//
//    import java.time.LocalDateTime;
//    import java.util.List;
//    import java.util.Optional;
//
//    public interface CartsService {
//        // Lấy danh sách tất cả giỏ hàng
//        List<Carts> getAllCarts();
//
//        // Lấy giỏ hàng theo CartID
//        Optional<Carts> getCartById(Integer cartId);
//
//        // Lấy giỏ hàng theo UserID
//        Optional<Carts> getCartByUserId(Integer userId);
//
//        // Tạo giở hàng
//        Carts createCart(Carts cart);
//
//        // Cập nhật trạng thái hoặc thông tin giỏ hàng
//        Carts updateCart(Integer cartId, Carts cart);
//
//        // Xóa giỏ hàng theo CartID
//        void deleteCart(Integer cartId);
//
//        // Xóa tất cả giỏ hàng theo UserID
//    //    void deleteCartsByUserId(Integer userId);
//
//
//    }

