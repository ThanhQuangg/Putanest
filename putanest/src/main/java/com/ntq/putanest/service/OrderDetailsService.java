package com.ntq.putanest.service;

import com.ntq.putanest.pojo.Orderdetails;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderDetailsService {
    List<Orderdetails> getAllOrderDetails();
    Optional<Orderdetails> getOrderDetailById(Integer orderDetailId);
    List<Orderdetails> getOrderDetailsByOrderId(Integer orderId);
    Orderdetails saveOrderDetail(Orderdetails orderDetail);
}

