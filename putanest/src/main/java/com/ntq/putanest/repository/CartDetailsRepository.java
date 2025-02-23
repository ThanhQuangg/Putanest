package com.ntq.putanest.repository;

import com.ntq.putanest.dto.CartdetailsDTO;
import com.ntq.putanest.pojo.Cartdetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartDetailsRepository extends JpaRepository<Cartdetails, Integer> {
    List<Cartdetails> findByCart_CartId(Integer cartId);
}

