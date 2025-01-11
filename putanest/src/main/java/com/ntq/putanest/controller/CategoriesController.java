package com.ntq.putanest.controller;

import com.ntq.putanest.dto.CategoriesDTO;
import com.ntq.putanest.pojo.Categories;
import com.ntq.putanest.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//@RestController
//    @RequestMapping("/api/categories")
//public class CategoriesController {
//
//    private final CategoriesService categoriesService;
//
//    @Autowired
//    public CategoriesController(CategoriesService categoriesService) {
//        this.categoriesService = categoriesService;
//    }
//
//    // Lấy tất cả danh mục
//    @GetMapping
//    public List<Categories> getAllCategories() {
//        return categoriesService.getAllCategories();
//    }
//
//    // Lấy danh mục theo ID
//    @GetMapping("/{id}")
//    public ResponseEntity<Categories> getCategoryById(@PathVariable("id") Integer id) {
//        Optional<Categories> category = categoriesService.getCategoryById(id);
//        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    // Tạo danh mục mới
//    @PostMapping
//    public ResponseEntity<Categories> createCategory(
//            @RequestParam("categoryName") String categoryName,
//            @RequestParam("description") String description) {
//
//        Categories category = new Categories();
//        category.setCategoryName(categoryName);
//        category.setDescription(description);
//        category.setCreatedAt(LocalDateTime.now());
//        category.setUpdatedAt(LocalDateTime.now());
//
//        Categories createdCategory = categoriesService.createCategory(category);
//        return ResponseEntity.ok(createdCategory);
//    }
//
//    // Cập nhật danh mục
//    @PutMapping("/{id}")
//    public ResponseEntity<Categories> updateCategory(
//            @PathVariable("id") Integer id,
//            @RequestParam("categoryName") String categoryName,
//            @RequestParam("description") String description) {
//
//        Categories category = new Categories();
//        category.setCategoryName(categoryName);
//        category.setDescription(description);
//        category.setUpdatedAt(LocalDateTime.now());
//
//        try {
//            Categories updatedCategory = categoriesService.updateCategory(id, category);
//            return ResponseEntity.ok(updatedCategory);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }
//
//    // Xóa danh mục
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Integer id) {
//        try {
//            categoriesService.deleteCategory(id);
//            return ResponseEntity.noContent().build();
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//}


@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    private final CategoriesService categoriesService;

    @Autowired
    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping
    public List<CategoriesDTO> getAllCategories() {
        return categoriesService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriesDTO> getCategoryById(@PathVariable("id") Integer id) {
        Optional<CategoriesDTO> category = categoriesService.getCategoryById(id);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoriesDTO> createCategory(@RequestBody CategoriesDTO categoryDTO) {
        CategoriesDTO createdCategory = categoriesService.createCategory(categoryDTO);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriesDTO> updateCategory(
            @PathVariable("id") Integer id,
            @RequestBody CategoriesDTO categoryDTO) {

        try {
            CategoriesDTO updatedCategory = categoriesService.updateCategory(id, categoryDTO);
            return ResponseEntity.ok(updatedCategory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Integer id) {
        try {
            categoriesService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}