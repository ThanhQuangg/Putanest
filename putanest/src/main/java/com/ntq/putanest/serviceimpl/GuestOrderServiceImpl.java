package com.ntq.putanest.serviceimpl;

import com.ntq.putanest.pojo.GuestOrders;
import com.ntq.putanest.repository.GuestOrderRepository;
import com.ntq.putanest.service.GuestOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuestOrderServiceImpl implements GuestOrderService {

    private final GuestOrderRepository guestOrderRepository;

    @Autowired
    public GuestOrderServiceImpl(GuestOrderRepository guestOrderRepository) {
        this.guestOrderRepository = guestOrderRepository;
    }

    @Override
    public GuestOrders createGuestOrder(GuestOrders guestOrder) {
        return guestOrderRepository.save(guestOrder);
    }

    @Override
    public Optional<GuestOrders> getGuestOrderByOrderCode(String orderCode) {
        return guestOrderRepository.findByOrderCode(orderCode);
    }

    @Override
    public List<GuestOrders> getAllGuestOrders() {
        return guestOrderRepository.findAll();
    }

    @Override
    public void deleteGuestOrder(Integer id) {
        guestOrderRepository.deleteById(id);
    }
}
