package com.ntq.putanest.repository;

import com.ntq.putanest.pojo.GuestOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestOrderRepository extends JpaRepository<GuestOrders, Integer> {
    Optional<GuestOrders> findByOrderCode(String orderCode);
}

