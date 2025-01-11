package com.ntq.putanest.controller;

import com.ntq.putanest.dto.CartsDTO;
import com.ntq.putanest.service.CartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartsController {

    private final CartsService cartsService;

    @Autowired
    public CartsController(CartsService cartsService) {
        this.cartsService = cartsService;
    }

    @GetMapping
    public List<CartsDTO> getAllCarts() {
        return cartsService.getAllCarts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartsDTO> getCartById(@PathVariable("id") Integer id) {
        Optional<CartsDTO> cart = cartsService.getCartById(id);
        return cart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable Integer userId) {
        Optional<CartsDTO> cart = cartsService.getCartByUserId(userId);
        return cart.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CartsDTO> createCart(@ModelAttribute CartsDTO cartDTO) {
        CartsDTO createdCart = cartsService.createCart(cartDTO);
        return ResponseEntity.ok(createdCart);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CartsDTO> updateCart(@PathVariable("id") Integer id, @ModelAttribute CartsDTO cartDTO) {
        try {
            CartsDTO updatedCart = cartsService.updateCart(id, cartDTO);
            return ResponseEntity.ok(updatedCart);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable("id") Integer id) {
        try {
            cartsService.deleteCart(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}



//package com.ntq.putanest.controller;
//
//import com.ntq.putanest.dto.CartsDTO;
//import com.ntq.putanest.pojo.Carts;
//import com.ntq.putanest.service.CartsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/carts")
//public class CartsController {
//
//    private final CartsService cartsService;
//
//    @Autowired
//    public CartsController(CartsService cartsService) {
//        this.cartsService = cartsService;
//    }
//
//    @GetMapping
//    public List<Carts> getAllCarts() {
//        return cartsService.getAllCarts();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Carts> getCartById(@PathVariable("id") Integer id) {
//        Optional<Carts> cart = cartsService.getCartById(id);
//        return cart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<?> getCartByUserId(@PathVariable Integer userId) {
//        Optional<Carts> cart = cartsService.getCartByUserId(userId);
//        if (cart.isPresent()) {
//            return ResponseEntity.ok(cart.get());
//        } else {
//            return ResponseEntity.status(404).body("Giỏ hàng không tìm thấy cho User ID: " + userId);
//        }
//    }
//
//    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<Carts> createCart(@ModelAttribute CartsDTO cartDTO)  {
//        Carts cart = new Carts();
//        // Chuyển đổi từ DTO sang entity
//        cart.setUserId(cartDTO.getUserId());
//        cart.setCreatedAt(cartDTO.getCreatedAt());
//        Carts createdCart = cartsService.createCart(cart);
//        return ResponseEntity.ok(createdCart);
//    }
//
//    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<Carts> updateCart(@PathVariable("id") Integer id, @ModelAttribute CartsDTO cartDTO)  {
//        try {
//            Carts cart = new Carts();
//            // Chuyển đổi từ DTO sang entity
//            cart.setUserId(cartDTO.getUserId());
//            cart.setCreatedAt(cartDTO.getCreatedAt());
//            // Các trường khác nếu có
//            Carts updatedCart = cartsService.updateCart(id, cart);
//            return ResponseEntity.ok(updatedCart);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCart(@PathVariable("id") Integer id) {
//        try {
//            cartsService.deleteCart(id);
//            return ResponseEntity.noContent().build();
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//}
