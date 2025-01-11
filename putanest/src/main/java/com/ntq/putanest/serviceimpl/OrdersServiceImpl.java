package com.ntq.putanest.serviceimpl;

import com.ntq.putanest.pojo.Orders;
import com.ntq.putanest.repository.OrdersRepository;
import com.ntq.putanest.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public Orders saveOrder(Orders order) {
        return ordersRepository.save(order);
    }
}

