package com.ntq.putanest.serviceimpl;

import com.ntq.putanest.dto.CategoriesDTO;
import com.ntq.putanest.pojo.Categories;
import com.ntq.putanest.repository.CategoriesRepository;
import com.ntq.putanest.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//@Service
//public class CategoriesServiceImpl implements CategoriesService {
//
//    @Autowired
//    private CategoriesRepository categoriesRepository;
//
//    @Override
//    public List<Categories> getAllCategories() {
//        return categoriesRepository.findAll();
//    }
//
//    @Override
//    public Optional<Categories> getCategoryById(Integer categoryId) {
//        return categoriesRepository.findById(categoryId);
//    }
//
//    @Override
//    public Categories createCategory(Categories category) {
//        category.setCreatedAt(LocalDateTime.now());
//        category.setUpdatedAt(LocalDateTime.now());
//        return categoriesRepository.save(category);
//    }
//
//    @Override
//    public Categories updateCategory(Integer categoryId, Categories category) {
//        if (categoriesRepository.existsById(categoryId)) {
//            category.setCategoryId(categoryId);
//            category.setUpdatedAt(LocalDateTime.now());
//            return categoriesRepository.save(category);
//        }
//        throw new IllegalArgumentException("Category ID không tồn tại.");
//    }
//
//    @Override
//    public void deleteCategory(Integer categoryId) {
//        if (categoriesRepository.existsById(categoryId)) {
//            categoriesRepository.deleteById(categoryId);
//        } else {
//            throw new IllegalArgumentException("Category ID không tồn tại.");
//        }
//    }
//}


@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Override
    public List<CategoriesDTO> getAllCategories() {
        List<Categories> categories = categoriesRepository.findAll();
        return categories.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<CategoriesDTO> getCategoryById(Integer categoryId) {
        Optional<Categories> category = categoriesRepository.findById(categoryId);
        return category.map(this::toDTO);
    }

    @Override
    public CategoriesDTO createCategory(CategoriesDTO categoryDTO) {
        Categories category = toEntity(categoryDTO);
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        Categories savedCategory = categoriesRepository.save(category);
        return toDTO(savedCategory);
    }

    @Override
    public CategoriesDTO updateCategory(Integer categoryId, CategoriesDTO categoryDTO) {
        if (categoriesRepository.existsById(categoryId)) {
            Categories category = toEntity(categoryDTO);
            category.setCategoryId(categoryId);
            category.setUpdatedAt(LocalDateTime.now());
            Categories updatedCategory = categoriesRepository.save(category);
            return toDTO(updatedCategory);
        }
        throw new IllegalArgumentException("Category ID không tồn tại.");
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        if (categoriesRepository.existsById(categoryId)) {
            categoriesRepository.deleteById(categoryId);
        } else {
            throw new IllegalArgumentException("Category ID không tồn tại.");
        }
    }

    private CategoriesDTO toDTO(Categories category) {
        CategoriesDTO dto = new CategoriesDTO();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        dto.setDescription(category.getDescription());
        dto.setCreatedAt(category.getCreatedAt());
        dto.setUpdatedAt(category.getUpdatedAt());
        return dto;
    }

    private Categories toEntity(CategoriesDTO dto) {
        Categories category = new Categories();
        category.setCategoryId(dto.getCategoryId());
        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());
        category.setCreatedAt(dto.getCreatedAt());
        category.setUpdatedAt(dto.getUpdatedAt());
        return category;
    }
}





























