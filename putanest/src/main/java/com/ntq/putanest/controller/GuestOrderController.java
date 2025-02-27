package com.ntq.putanest.controller;

import com.ntq.putanest.dto.GuestOrderDetailsDTO;
import com.ntq.putanest.dto.GuestOrdersDTO;
import com.ntq.putanest.pojo.GuestOrderDetails;
import com.ntq.putanest.pojo.GuestOrders;
import com.ntq.putanest.service.GuestOrderDetailService;
import com.ntq.putanest.service.GuestOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/guest-orders")
public class GuestOrderController {

    private final GuestOrderService guestOrderService;
    private final GuestOrderDetailService guestOrderDetailService;

    @Autowired
    public GuestOrderController(GuestOrderService guestOrderService, GuestOrderDetailService guestOrderDetailService) {
        this.guestOrderService = guestOrderService;
        this.guestOrderDetailService = guestOrderDetailService;
    }

    @PostMapping
    public ResponseEntity<GuestOrders> createGuestOrder(@RequestBody GuestOrdersDTO guestOrderDTO) {
        // Tính toán tổng tiền
        BigDecimal totalAmount = guestOrderDTO.getGuestOrderDetails().stream()
                .map(detail -> detail.getPrice().multiply(BigDecimal.valueOf(detail.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Tạo đơn hàng
        GuestOrders guestOrder = new GuestOrders();
        guestOrder.setCustomerName(guestOrderDTO.getCustomerName());
        guestOrder.setPhoneNumber(guestOrderDTO.getPhoneNumber());
        guestOrder.setEmail(guestOrderDTO.getEmail());
        guestOrder.setAddress(guestOrderDTO.getAddress());
        guestOrder.setTotalAmount(totalAmount);
        guestOrder.setOrderCode(generateOrderCode());
        guestOrder.setStatus("PENDING");

        GuestOrders savedOrder = guestOrderService.createGuestOrder(guestOrder);

        // Tạo chi tiết đơn hàng
        guestOrderDTO.getGuestOrderDetails().forEach(detail -> {
            GuestOrderDetails guestOrderDetail = new GuestOrderDetails();
            guestOrderDetail.setGuestOrder(savedOrder);
            guestOrderDetail.setProductId(detail.getProductId());
            guestOrderDetail.setQuantity(detail.getQuantity());
            guestOrderDetail.setPrice(detail.getPrice());

            guestOrderDetailService.addGuestOrderDetail(guestOrderDetail);
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    private String generateOrderCode() {
        return "GUEST-" + System.currentTimeMillis();
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<GuestOrders> getGuestOrderById(@PathVariable Integer id) {
//        Optional<GuestOrders> guestOrder = guestOrderService.getGuestOrderById(id);
//        return guestOrder.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
//    }

    @GetMapping("/order-code/{orderCode}")
    public ResponseEntity<GuestOrders> getGuestOrderByOrderCode(@PathVariable String orderCode) {
        Optional<GuestOrders> guestOrder = guestOrderService.getGuestOrderByOrderCode(orderCode);
        return guestOrder.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<GuestOrders>> getAllGuestOrders() {
        List<GuestOrders> guestOrders = guestOrderService.getAllGuestOrders();
        return ResponseEntity.ok(guestOrders);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuestOrder(@PathVariable Integer id) {
        // Xóa chi tiết đơn hàng trước
        guestOrderDetailService.deleteByGuestOrder_GuestOrderId(id);

        // Sau đó xóa đơn hàng chính
        guestOrderService.deleteGuestOrder(id);
        return ResponseEntity.noContent().build();
    }
}
