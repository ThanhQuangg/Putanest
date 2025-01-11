package com.ntq.putanest.repository;

import com.ntq.putanest.pojo.Orderdetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<Orderdetails, Integer> {
    List<Orderdetails> findByOrderId(Integer orderId);
}

