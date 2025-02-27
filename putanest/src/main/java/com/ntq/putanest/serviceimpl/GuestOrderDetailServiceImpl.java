package com.ntq.putanest.serviceimpl;

import com.ntq.putanest.pojo.GuestOrderDetails;
import com.ntq.putanest.repository.GuestOrderDetailRepository;
import com.ntq.putanest.service.GuestOrderDetailService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GuestOrderDetailServiceImpl implements GuestOrderDetailService {

    private final GuestOrderDetailRepository guestOrderDetailRepository;

    public GuestOrderDetailServiceImpl(GuestOrderDetailRepository guestOrderDetailRepository) {
        this.guestOrderDetailRepository = guestOrderDetailRepository;
    }

    @Override
    @Transactional
    public void addGuestOrderDetail(GuestOrderDetails guestOrderDetail) {
        guestOrderDetailRepository.save(guestOrderDetail);
    }

    @Override
    public List<GuestOrderDetails> findByGuestOrder_GuestOrderId(Integer guestOrderId) {
        return guestOrderDetailRepository.findByGuestOrder_GuestOrderId(guestOrderId);
    }

    @Override
    @Transactional
    public void deleteByGuestOrder_GuestOrderId(int guestOrderId) {
        if (guestOrderDetailRepository.findByGuestOrder_GuestOrderId(guestOrderId).isEmpty()) {
            throw new EntityNotFoundException("Guest order details not found for ID: " + guestOrderId);
        }
        guestOrderDetailRepository.deleteByGuestOrder_GuestOrderId(guestOrderId);
    }
}
