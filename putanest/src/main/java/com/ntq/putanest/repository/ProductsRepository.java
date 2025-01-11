package com.ntq.putanest.repository;

import com.ntq.putanest.pojo.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer> {
    // Tìm danh sách sản phẩm theo CategoryID
    List<Products> findByCategory_CategoryId(Integer categoryId);
    List<Products> findByProductNameContainingIgnoreCase(String productName);
}
