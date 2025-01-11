package com.ntq.putanest.repository;

import com.ntq.putanest.pojo.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByUserId(Integer userId);
}



