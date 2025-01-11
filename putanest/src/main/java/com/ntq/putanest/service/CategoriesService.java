package com.ntq.putanest.service;

import com.ntq.putanest.pojo.Categories;
import java.util.List;
import java.util.Optional;
//
//public interface CategoriesService {
//    // Lấy danh sách tất cả danh mục
//    List<Categories> getAllCategories();
//
//    // Lấy danh mục theo ID
//    Optional<Categories> getCategoryById(Integer categoryId);
//
//    // Tạo mới một danh mục
//    Categories createCategory(Categories category);
//
//    // Cập nhật một danh mục
//    Categories updateCategory(Integer categoryId, Categories category);
//
//    // Xóa một danh mục
//    void deleteCategory(Integer categoryId);
//}
//

import com.ntq.putanest.dto.CategoriesDTO;

public interface CategoriesService {
    List<CategoriesDTO> getAllCategories();
    Optional<CategoriesDTO> getCategoryById(Integer categoryId);
    CategoriesDTO createCategory(CategoriesDTO categoryDTO);
    CategoriesDTO updateCategory(Integer categoryId, CategoriesDTO categoryDTO);
    void deleteCategory(Integer categoryId);
}
