package com.ntq.putanest.serviceimpl;

import com.ntq.putanest.dto.CartsDTO;
import com.ntq.putanest.pojo.Carts;
import com.ntq.putanest.repository.CartsRepository;
import com.ntq.putanest.service.CartsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartsServiceImpl implements CartsService {

    private final CartsRepository cartsRepository;

    @Autowired
    public CartsServiceImpl(CartsRepository cartsRepository) {
        this.cartsRepository = cartsRepository;
    }

    @Override
    public List<CartsDTO> getAllCarts() {
        return cartsRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CartsDTO> getCartById(Integer cartId) {
        return cartsRepository.findById(cartId)
                .map(this::convertToDTO);
    }

    @Override
    public Optional<CartsDTO> getCartByUserId(Integer userId) {
        return cartsRepository.findByUserId(userId)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public CartsDTO createCart(CartsDTO cartDTO) {
        if (cartsRepository.findByUserId(cartDTO.getUserId()).isPresent()) {
            throw new IllegalStateException("Người dùng đã có giỏ hàng.");
        }
        Carts cart = convertToEntity(cartDTO);
        cart.setCreatedAt(LocalDateTime.now());
        Carts savedCart = cartsRepository.save(cart);
        return convertToDTO(savedCart);
    }

    @Override
    @Transactional
    public CartsDTO updateCart(Integer cartId, CartsDTO cartDTO) {
        if (!cartsRepository.existsById(cartId)) {
            throw new IllegalArgumentException("Cart ID không tồn tại.");
        }
        Carts cart = convertToEntity(cartDTO);
        cart.setCartId(cartId);
        Carts updatedCart = cartsRepository.save(cart);
        return convertToDTO(updatedCart);
    }

    @Override
    @Transactional
    public void deleteCart(Integer cartId) {
        if (cartsRepository.existsById(cartId)) {
            cartsRepository.deleteById(cartId);
        } else {
            throw new IllegalArgumentException("Cart ID không tồn tại.");
        }
    }

    private CartsDTO convertToDTO(Carts cart) {
        CartsDTO dto = new CartsDTO();
        dto.setCartId(cart.getCartId());
        dto.setUserId(cart.getUserId());
        dto.setCreatedAt(cart.getCreatedAt());
        return dto;
    }

    private Carts convertToEntity(CartsDTO dto) {
        Carts cart = new Carts();
        cart.setCartId(dto.getCartId());
        cart.setUserId(dto.getUserId());
        cart.setCreatedAt(dto.getCreatedAt());
        return cart;
    }
}


//@Service
//public class CartsServiceImpl implements CartsService {
//
//    @Autowired
//    private CartsRepository cartsRepository;
//
//    @Override
//    public List<Carts> getAllCarts() {
//        return cartsRepository.findAll();
//    }
//
//    @Override
//    public Optional<Carts> getCartById(Integer cartId) {
//        return cartsRepository.findById(cartId);
//    }
//
//    @Override
//    public Optional<Carts> getCartByUserId(Integer userId) {
//        return cartsRepository.findByUserId(userId);
//    }
//
//    @Override
//    @Transactional
//    public Carts createCart(Carts cart) {
//        Optional<Carts> existingCart = cartsRepository.findByUserId(cart.getUserId());
//        if (existingCart.isPresent()) {
//            throw new IllegalStateException("Người dùng đã có giỏ hàng.");
//        }
//        cart.setCreatedAt(LocalDateTime.now());
//        return cartsRepository.save(cart);
//    }
//
//    @Override
//    @Transactional
//    public Carts updateCart(Integer cartId, Carts cart) {
//        if (cartsRepository.existsById(cartId)) {
//            cart.setCartId(cartId);
//            return cartsRepository.save(cart);
//        }
//        throw new IllegalArgumentException("Cart ID không tồn tại.");
//    }
//
//    @Override
//    @Transactional
//    public void deleteCart(Integer cartId) {
//        if (cartsRepository.existsById(cartId)) {
//            cartsRepository.deleteById(cartId);
//        } else {
//            throw new IllegalArgumentException("Cart ID không tồn tại.");
//        }
//    }
//}


