package com.ntq.putanest.repository;

import com.ntq.putanest.pojo.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer> {
    // Tìm danh sách sản phẩm theo CategoryID
    List<Products> findByCategory_CategoryId(Integer categoryId);
    List<Products> findByProductNameContainingIgnoreCase(String productName);

    Page<Products> findAll(Pageable pageable);

    @Query("SELECT p FROM Products p WHERE " +
            "(:productName IS NULL OR p.productName LIKE %:productName%) AND " +
            "(:description IS NULL OR p.description LIKE %:description%) AND " +
            "(:price IS NULL OR p.price = :price) AND " +
            "(:quantity IS NULL OR p.quantity = :quantity) AND " +
            "(:categoryId IS NULL OR p.category.categoryId = :categoryId)")
    List<Products> searchProducts(
            @Param("productName") String productName,
            @Param("description") String description,
            @Param("price") BigDecimal price,
            @Param("quantity") Integer quantity,
            @Param("categoryId") Integer categoryId
    );
}
