package com.ntq.putanest.repository;

import com.ntq.putanest.pojo.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
}

