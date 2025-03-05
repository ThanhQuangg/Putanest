package com.ntq.putanest.repository;

import com.ntq.putanest.pojo.Orders;
import com.ntq.putanest.pojo.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByUserId(Integer userId);

    Page<Orders> findAll(Pageable pageable);

    Page<Orders> findByUserId(Integer userId, Pageable pageable);

    @Query("SELECT o FROM Orders o WHERE " +
            "(:userId IS NULL OR o.userId = :userId) AND " +
            "(:totalAmount IS NULL OR o.totalAmount = :totalAmount) AND " +
            "(:orderStatus IS NULL OR o.orderStatus LIKE %:orderStatus%) AND " +
            "(:createdAt IS NULL OR o.createdAt = :createdAt)")
    List<Orders> searchOrders(
            @Param("userId") Integer userId,
            @Param("totalAmount") BigDecimal totalAmount,
            @Param("orderStatus") String orderStatus,
            @Param("createdAt") LocalDateTime createdAt
    );
}



