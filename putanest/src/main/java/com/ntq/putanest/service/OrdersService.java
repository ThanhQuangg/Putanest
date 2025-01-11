package com.ntq.putanest.service;

import com.ntq.putanest.pojo.Orders;
import java.util.List;
import java.util.Optional;

public interface OrdersService {
    List<Orders> getAllOrders();
    Optional<Orders> getOrderById(Integer orderId);
    List<Orders> getOrdersByUserId(Integer userId);
    Orders saveOrder(Orders order);
}


