package com.ntq.putanest.controller;


import com.ntq.putanest.dto.OrderDetailRequest;
import com.ntq.putanest.dto.OrderRequest;
import com.ntq.putanest.pojo.Orderdetails;
import com.ntq.putanest.pojo.Orders;
import com.ntq.putanest.pojo.Products;
import com.ntq.putanest.service.OrderDetailsService;
import com.ntq.putanest.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrderDetailsService orderDetailsService;

    @PostMapping
    public ResponseEntity<Orders> createOrder(@RequestBody OrderRequest orderRequest) {
        // Tạo đơn hàng mới
        Orders newOrder = new Orders();
        newOrder.setUserId(orderRequest.getUserId());
        newOrder.setOrderStatus("Đang chờ xác nhận từ người bán");
        newOrder.setCreatedAt(LocalDateTime.now());
        newOrder.setUpdatedAt(LocalDateTime.now());
        // Tính toán totalAmount
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderDetailRequest detailRequest : orderRequest.getOrderDetails()) {
            totalAmount = totalAmount.add(detailRequest.getPrice().multiply(BigDecimal.valueOf(detailRequest.getQuantity())));
        }

        newOrder.setTotalAmount(totalAmount);
        Orders savedOrder = ordersService.saveOrder(newOrder);

        // Tạo chi tiết đơn hàng
        for (OrderDetailRequest detailRequest : orderRequest.getOrderDetails()) {
            Orderdetails orderDetail = new Orderdetails();
            orderDetail.setOrderId(savedOrder.getOrderId());
            orderDetail.setProductId(detailRequest.getProductId());
            orderDetail.setQuantity(detailRequest.getQuantity());
            orderDetail.setPrice(detailRequest.getPrice());
            orderDetail.setOrder(savedOrder);

            orderDetailsService.saveOrderDetail(orderDetail);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Orders>> getOrdersByUserId(@PathVariable Integer userId) {
        List<Orders> orders = ordersService.getOrdersByUserId(userId);

        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Nếu không có đơn hàng nào, trả về 404
        }

        return ResponseEntity.ok(orders);
    }

    @GetMapping
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> orders = ordersService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/paginated")
    public ResponseEntity<Page<Orders>> getPaginatedOrders(
            @RequestParam(defaultValue = "0") int page,  // Trang mặc định là 0
            @RequestParam(defaultValue = "2") int size // Kích thước mặc định là 10
    ) {
        Page<Orders> paginatedOrders = ordersService.getPaginatedOrders(page, size);
        return ResponseEntity.ok(paginatedOrders);
    }

    @GetMapping("/paginated-by-user")
    public ResponseEntity<Page<Orders>> getPaginatedOrdersByUserId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam Integer userId
    ) {
        Page<Orders> paginatedOrders = ordersService.getPaginatedOrdersByUserId(page, size, userId);
        return ResponseEntity.ok(paginatedOrders);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Orders>> searchOrder(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) BigDecimal totalAmount,
            @RequestParam(required = false) String orderStatus,
            @RequestParam(required = false) LocalDateTime createdAt
    ) {
        List<Orders> orders = ordersService.searchOrder(userId, totalAmount, orderStatus, createdAt);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable Integer orderId,
            @RequestBody String status) {
        ordersService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(status);
    }
}
