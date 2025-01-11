package com.ntq.putanest.service;

import com.ntq.putanest.pojo.Cartdetails;
import com.ntq.putanest.dto.CartdetailsDTO;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CartDetailsService {
    List<CartdetailsDTO> getAllCartDetails();

    Optional<CartdetailsDTO> getCartDetailById(Integer cartDetailId);

    CartdetailsDTO createCartDetail(CartdetailsDTO cartDetailDTO);

    CartdetailsDTO updateCartDetail(Integer cartDetailId, CartdetailsDTO cartDetailDTO);

    void deleteCartDetail(Integer cartDetailId);

    BigDecimal calculateTotalPrice(Integer cartId);

    List<CartdetailsDTO> findByCartId(Integer cartId);
}

//public interface CartDetailsService {
//    // Lấy danh sách tất cả CartDetails
//    List<Cartdetails> getAllCartDetails();
//
//    // Lấy CartDetails theo CartDetailID
//    Optional<Cartdetails> getCartDetailById(Integer cartDetailId);
//
//    // Tạo mới một CartDetails
//    Cartdetails createCartDetail(Cartdetails cartDetail);
//
//
//
//    // Cập nhật thông tin một CartDetails
//    Cartdetails updateCartDetail(Integer cartDetailId, Cartdetails cartDetail);
//
//    // Xóa một CartDetails
//    void deleteCartDetail(Integer cartDetailId);
//
//    // Tính tổng tiền của một Cart
//    BigDecimal calculateTotalPrice(Integer cartId);
//}

