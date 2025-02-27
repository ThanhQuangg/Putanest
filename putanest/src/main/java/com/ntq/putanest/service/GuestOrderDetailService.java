package com.ntq.putanest.service;

import com.ntq.putanest.pojo.GuestOrderDetails;
import java.util.List;

public interface GuestOrderDetailService {
    void addGuestOrderDetail(GuestOrderDetails guestOrderDetail);
    List<GuestOrderDetails> findByGuestOrder_GuestOrderId(Integer guestOrderId);
    void deleteByGuestOrder_GuestOrderId(int guestOrderId);

}