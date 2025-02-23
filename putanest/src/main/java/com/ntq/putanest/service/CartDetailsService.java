package com.ntq.putanest.service;

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


