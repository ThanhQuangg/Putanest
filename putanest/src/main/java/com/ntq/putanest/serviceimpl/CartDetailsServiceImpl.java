package com.ntq.putanest.serviceimpl;

import com.ntq.putanest.pojo.Cartdetails;
import com.ntq.putanest.pojo.Products;
import com.ntq.putanest.repository.CartDetailsRepository;
import com.ntq.putanest.repository.CartsRepository;
import com.ntq.putanest.repository.ProductsRepository;
import com.ntq.putanest.service.CartDetailsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ntq.putanest.dto.CartdetailsDTO;
import org.springframework.beans.BeanUtils;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartDetailsServiceImpl implements CartDetailsService {

    @Autowired
    private CartDetailsRepository cartDetailsRepository;

    @Autowired
    private CartsRepository cartsRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public List<CartdetailsDTO> getAllCartDetails() {
        return cartDetailsRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public Optional<CartdetailsDTO> getCartDetailById(Integer cartDetailId) {
        return cartDetailsRepository.findById(cartDetailId)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public CartdetailsDTO createCartDetail(CartdetailsDTO cartDetailDTO) {
        Cartdetails cartDetail = convertToEntity(cartDetailDTO);
        Cartdetails savedCartDetail = cartDetailsRepository.save(cartDetail);
        return convertToDTO(savedCartDetail);
    }

    @Override
    @Transactional
    public CartdetailsDTO updateCartDetail(Integer cartDetailId, CartdetailsDTO cartDetailDTO) {
        if (!cartDetailsRepository.existsById(cartDetailId)) {
            throw new IllegalArgumentException("Cart detail ID không tồn tại.");
        }
        Cartdetails cartDetail = convertToEntity(cartDetailDTO);
        cartDetail.setCartDetailId(cartDetailId);
        Cartdetails updatedCartDetail = cartDetailsRepository.save(cartDetail);
        return convertToDTO(updatedCartDetail);
    }

    @Override
    @Transactional
    public void deleteCartDetail(Integer cartDetailId) {
        if (cartDetailsRepository.existsById(cartDetailId)) {
            cartDetailsRepository.deleteById(cartDetailId);
        } else {
            throw new IllegalArgumentException("Cart detail ID không tồn tại.");
        }
    }

    @Override
    public BigDecimal calculateTotalPrice(Integer cartId) {
        List<Cartdetails> cartDetailsList = cartDetailsRepository.findByCart_CartId(cartId);
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (Cartdetails cartDetail : cartDetailsList) {
            Products product = productsRepository.findById(cartDetail.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            BigDecimal productTotal = product.getPrice().multiply(new BigDecimal(cartDetail.getQuantity()));
            totalPrice = totalPrice.add(productTotal);
        }
        return totalPrice;
    }

    @Override
    public List<CartdetailsDTO> findByCartId(Integer cartId) {
        List<Cartdetails> cartDetailsList = cartDetailsRepository.findByCart_CartId(cartId);
        return cartDetailsList.stream()
                .map(this::convertToDTO)
                .toList();
    }

    private CartdetailsDTO convertToDTO(Cartdetails cartDetail) {
        CartdetailsDTO dto = new CartdetailsDTO();
        BeanUtils.copyProperties(cartDetail, dto);
        dto.setCartId(cartDetail.getCart().getCartId());
        Products product = productsRepository.findById(cartDetail.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        dto.setProductName(product.getProductName()); // Đặt tên sản phẩm vào DTO
        return dto;
    }

    private Cartdetails convertToEntity(CartdetailsDTO dto) {
        Cartdetails cartDetail = new Cartdetails();
        BeanUtils.copyProperties(dto, cartDetail);
        cartDetail.setCart(cartsRepository.findById(dto.getCartId())
                .orElseThrow(() -> new IllegalArgumentException("Cart ID không tồn tại.")));
        return cartDetail;
    }


}