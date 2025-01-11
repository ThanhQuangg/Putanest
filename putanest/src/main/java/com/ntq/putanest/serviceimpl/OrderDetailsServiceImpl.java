package com.ntq.putanest.serviceimpl;

import com.ntq.putanest.pojo.Orderdetails;
import com.ntq.putanest.repository.OrderDetailsRepository;
import com.ntq.putanest.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Override
    public List<Orderdetails> getAllOrderDetails() {
        return orderDetailsRepository.findAll();
    }

    @Override
    public Optional<Orderdetails> getOrderDetailById(Integer orderDetailId) {
        return orderDetailsRepository.findById(orderDetailId);
    }

    @Override
    public List<Orderdetails> getOrderDetailsByOrderId(Integer orderId) {
        return orderDetailsRepository.findByOrderId(orderId);
    }

    @Override
    public Orderdetails saveOrderDetail(Orderdetails orderDetail) {
        return orderDetailsRepository.save(orderDetail);
    }
}

