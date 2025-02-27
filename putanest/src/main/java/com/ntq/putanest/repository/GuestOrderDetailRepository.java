package com.ntq.putanest.repository;

import com.ntq.putanest.pojo.GuestOrderDetails;
import com.ntq.putanest.pojo.GuestOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestOrderDetailRepository extends JpaRepository<GuestOrderDetails, Integer> {
    GuestOrderDetails save(GuestOrderDetails detail);

    // Tìm theo guestOrderId
    List<GuestOrderDetails> findByGuestOrder_GuestOrderId(Integer guestOrderId);

    // Xóa theo guestOrderId
    void deleteByGuestOrder_GuestOrderId(int guestOrderId);
}
