package com.ntq.putanest.repository;

import com.ntq.putanest.pojo.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartsRepository extends JpaRepository<Carts, Integer> {
    Optional<Carts> findByUserId(Integer userId);
}

