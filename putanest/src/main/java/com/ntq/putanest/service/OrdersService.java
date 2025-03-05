package com.ntq.putanest.service;

import com.ntq.putanest.pojo.Orders;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrdersService {
    List<Orders> getAllOrders();
    Optional<Orders> getOrderById(Integer orderId);
    List<Orders> getOrdersByUserId(Integer userId);
    Orders saveOrder(Orders order);
    public void updateOrderStatus(Integer orderId, String status);

    Page<Orders> getPaginatedOrders(int page, int size);
    Page<Orders> getPaginatedOrdersByUserId(int page, int size, Integer userId);
    List<Orders> searchOrder(Integer userId, BigDecimal totalAmount, String orderStatus, LocalDateTime createdAt);
}


