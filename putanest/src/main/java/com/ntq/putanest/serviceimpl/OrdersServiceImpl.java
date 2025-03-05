package com.ntq.putanest.serviceimpl;

import com.ntq.putanest.pojo.Orders;
import com.ntq.putanest.pojo.Products;
import com.ntq.putanest.repository.OrdersRepository;
import com.ntq.putanest.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    @Override
    public Optional<Orders> getOrderById(Integer orderId) {
        return ordersRepository.findById(orderId);
    }

    @Override
    public List<Orders> getOrdersByUserId(Integer userId) {
        return ordersRepository.findByUserId(userId);
    }

    @Override
    public Page<Orders> getPaginatedOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ordersRepository.findAll(pageable);
    }

    @Override
    public Page<Orders> getPaginatedOrdersByUserId(int page, int size, Integer userId) {
        Pageable pageable = PageRequest.of(page, size);
        return ordersRepository.findByUserId(userId, pageable);
    }

    @Override
    public List<Orders> searchOrder(Integer userId, BigDecimal totalAmount, String orderStatus, LocalDateTime createdAt) {
        return ordersRepository.searchOrders(userId, totalAmount, orderStatus, createdAt);
    }

    @Override
    public Orders saveOrder(Orders order) {
        return ordersRepository.save(order);
    }

    @Override
    public void updateOrderStatus(Integer orderId, String status) {
        // Tìm order theo ID
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        // Cập nhật trạng thái
        order.setOrderStatus(status);

        // Lưu lại thay đổi
        ordersRepository.save(order);
    }
}

