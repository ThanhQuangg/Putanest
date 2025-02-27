package com.ntq.putanest.service;

import com.ntq.putanest.pojo.GuestOrders;

import java.util.List;
import java.util.Optional;

public interface GuestOrderService {
    GuestOrders createGuestOrder(GuestOrders guestOrder);
    Optional<GuestOrders> getGuestOrderByOrderCode(String orderCode);
    List<GuestOrders> getAllGuestOrders();
    void deleteGuestOrder(Integer id);
}