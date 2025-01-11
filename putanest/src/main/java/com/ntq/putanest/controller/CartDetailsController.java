



package com.ntq.putanest.controller;

import com.ntq.putanest.dto.CartdetailsDTO;
import com.ntq.putanest.dto.CartsDTO;
import com.ntq.putanest.pojo.Cartdetails;
import com.ntq.putanest.pojo.Carts;
import com.ntq.putanest.service.CartDetailsService;
import com.ntq.putanest.service.CartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cartdetails")
public class CartDetailsController {

    private final CartDetailsService cartDetailsService;
    private final CartsService cartsService;
    @Autowired
    public CartDetailsController(CartDetailsService cartDetailsService, CartsService cartsService) {
        this.cartDetailsService = cartDetailsService;
        this.cartsService = cartsService;
    }

    @GetMapping
    public List<CartdetailsDTO> getAllCartDetails() {
        return cartDetailsService.getAllCartDetails();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartdetailsDTO> getCartDetailById(@PathVariable("id") Integer id) {
        Optional<CartdetailsDTO> cartDetail = cartDetailsService.getCartDetailById(id);
        return cartDetail.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<CartdetailsDTO>> getCartDetailsByCartId(@PathVariable Integer cartId) {
        List<CartdetailsDTO> cartDetails = cartDetailsService.findByCartId(cartId);
        if (cartDetails.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartDetails);
    }

    @GetMapping("/total-price")
    public ResponseEntity<BigDecimal> calculateTotalPrice(@RequestParam("cartId") Integer cartId) {
        try {
            BigDecimal totalPrice = cartDetailsService.calculateTotalPrice(cartId);
            return ResponseEntity.ok(totalPrice);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BigDecimal.ZERO);
        }
    }

    @PostMapping
    public ResponseEntity<CartdetailsDTO> createCartDetail(@RequestBody CartdetailsDTO cartDetailDTO) {
        CartdetailsDTO createdCartDetail = cartDetailsService.createCartDetail(cartDetailDTO);
        return ResponseEntity.ok(createdCartDetail);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartdetailsDTO> updateCartDetail(
            @PathVariable("id") Integer id,
            @RequestBody CartdetailsDTO cartDetailDTO) {
        try {
            CartdetailsDTO updatedCartDetail = cartDetailsService.updateCartDetail(id, cartDetailDTO);
            return ResponseEntity.ok(updatedCartDetail);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartDetail(@PathVariable("id") Integer id) {
        cartDetailsService.deleteCartDetail(id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping
//    public List<Cartdetails> getAllCartDetails() {
//        return cartDetailsService.getAllCartDetails();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Cartdetails> getCartDetailById(@PathVariable("id") Integer id) {
//        Optional<Cartdetails> cartDetail = cartDetailsService.getCartDetailById(id);
//        return cartDetail.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @PostMapping
//    public ResponseEntity<Cartdetails> createCartDetail(@ModelAttribute CartdetailsDTO cartDetailDTO)  {
//        Cartdetails cartDetail = new Cartdetails();
//        // Lấy DTO và chuyển đổi sang entity Carts
//        Optional<CartsDTO> cartDTO = cartsService.getCartById(cartDetailDTO.getCartId());
//        if (cartDTO.isEmpty()) {
//            throw new IllegalArgumentException("Cart ID không tồn tại.");
//        }
//        Carts cart = new Carts();
//        cart.setCartId(cartDTO.get().getCartId());
//        cart.setUserId(cartDTO.get().getUserId());
//        cart.setCreatedAt(cartDTO.get().getCreatedAt());
//
//        cartDetail.setCart(cart);
//        cartDetail.setProductId(cartDetailDTO.getProductId());
//        cartDetail.setQuantity(cartDetailDTO.getQuantity());
//        cartDetail.setPrice(cartDetailDTO.getPrice());
//
//        Cartdetails createdCartDetail = cartDetailsService.createCartDetail(cartDetail);
//        return ResponseEntity.ok(createdCartDetail);
//    }
//
//
//    @PutMapping(value = "/{id}")
//    public ResponseEntity<Cartdetails> updateCartDetail(@PathVariable("id") Integer id, @ModelAttribute CartdetailsDTO cartDetailDTO) {
//        try {
//            Cartdetails cartDetail = new Cartdetails();
//
//            // Lấy DTO và chuyển đổi sang entity Carts
//            Optional<CartsDTO> cartDTO = cartsService.getCartById(cartDetailDTO.getCartId());
//            if (cartDTO.isEmpty()) {
//                throw new IllegalArgumentException("Cart ID không tồn tại.");
//            }
//
//            Carts cart = new Carts();
//            cart.setCartId(cartDTO.get().getCartId());
//            cart.setUserId(cartDTO.get().getUserId());
//            cart.setCreatedAt(cartDTO.get().getCreatedAt());
//
//            cartDetail.setCart(cart);
//            cartDetail.setProductId(cartDetailDTO.getProductId());
//            cartDetail.setQuantity(cartDetailDTO.getQuantity());
//            cartDetail.setPrice(cartDetailDTO.getPrice());
//
//            Cartdetails updatedCartDetail = cartDetailsService.updateCartDetail(id, cartDetail);
//            return ResponseEntity.ok(updatedCartDetail);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }
//
//    @PutMapping(value = "/{id}/quantity")
//    public ResponseEntity<Cartdetails> updateCartQuantity(@PathVariable("id") Integer id, @RequestParam("quantity") Integer quantity) {
//        try {
//            Cartdetails cartDetail = cartDetailsService.getCartDetailById(id)
//                    .orElseThrow(() -> new IllegalArgumentException("Cart Detail ID không tồn tại."));
//            cartDetail.setQuantity(quantity);
//
//            Cartdetails updatedCartDetail = cartDetailsService.updateCartDetail(id, cartDetail);
//            return ResponseEntity.ok(updatedCartDetail);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCartDetail(@PathVariable("id") Integer id) {
//        cartDetailsService.deleteCartDetail(id);
//        return ResponseEntity.noContent().build();
//    }
}

