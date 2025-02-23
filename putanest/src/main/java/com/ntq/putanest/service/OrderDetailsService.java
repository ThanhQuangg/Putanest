package com.ntq.putanest.service;

import com.ntq.putanest.dto.OrderdetailsDTO;
import com.ntq.putanest.pojo.Orderdetails;

import java.util.List;
import java.util.Optional;

public interface OrderDetailsService {
    List<OrderdetailsDTO> getAllOrderDetails();

    Optional<OrderdetailsDTO> getOrderDetailById(Integer orderDetailId);

    List<OrderdetailsDTO> getOrderDetailsByOrderId(Integer orderId);

    Orderdetails saveOrderDetail(Orderdetails orderDetail);
}

