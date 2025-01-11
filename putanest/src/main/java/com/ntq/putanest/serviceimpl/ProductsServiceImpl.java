//package com.ntq.putanest.serviceimpl;
//
//import com.ntq.putanest.dto.ProductsDTO;
//import com.ntq.putanest.pojo.Categories;
//import com.ntq.putanest.pojo.Products;
//import com.ntq.putanest.repository.CategoriesRepository;
//import com.ntq.putanest.repository.ProductsRepository;
//import com.ntq.putanest.service.CloudinaryService;
//import com.ntq.putanest.service.ProductsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class ProductsServiceImpl implements ProductsService {
//
//    private final ProductsRepository productsRepository;
//    private final CloudinaryService cloudinaryService;
//    private final CategoriesRepository categoriesRepository;
//
//    @Autowired
//    public ProductsServiceImpl(ProductsRepository productsRepository, CloudinaryService cloudinaryService, CategoriesRepository categoriesRepository) {
//        this.productsRepository = productsRepository;
//        this.cloudinaryService = cloudinaryService;
//        this.categoriesRepository = categoriesRepository;
//    }
//
//    @Override
//    public List<ProductsDTO> getAllProducts() {
//        return productsRepository.findAll().stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public Optional<ProductsDTO> getProductById(Integer productId) {
//        return productsRepository.findById(productId)
//                .map(this::convertToDTO);
//    }
//
//    @Override
//    public List<ProductsDTO> getProductsByCategoryId(Integer categoryId) {
//        return productsRepository.findByCategory_CategoryId(categoryId).stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public Products createProduct(Products product, MultipartFile avatar) throws IOException {
//        // Kiểm tra và gán giá trị cho category
//        if (product.getCategory() == null || product.getCategory().getCategoryId() == null) {
//            throw new RuntimeException("Category must be provided");
//        }
//
//        Categories category = categoriesRepository.findById(product.getCategory().getCategoryId())
//                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + product.getCategory().getCategoryId()));
//        product.setCategory(category);
//
//        if (avatar != null && !avatar.isEmpty()) {
//            try {
//                String avatarUrl = cloudinaryService.uploadImage(avatar);
//                product.setAvatar(avatarUrl);
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to upload avatar", e);
//            }
//        }
//        product.setCreatedAt(LocalDateTime.now());
//        product.setUpdatedAt(LocalDateTime.now());
//        return productsRepository.save(product);
//    }
//
//    @Override
//    public Products updateProduct(Integer productId, ProductsDTO updateProductDTO, MultipartFile avatar) throws IOException {
//        Products existingProduct = productsRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
//
//        existingProduct.setProductName(updateProductDTO.getProductName());
//        existingProduct.setDescription(updateProductDTO.getDescription());
//        existingProduct.setPrice(updateProductDTO.getPrice());
//        existingProduct.setQuantity(updateProductDTO.getQuantity());
//
//        Categories category = categoriesRepository.findById(updateProductDTO.getCategoryId())
//                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + updateProductDTO.getCategoryId()));
//        existingProduct.setCategory(category);
//
//        // Kiểm tra và gán giá trị cho category
//        if (updateProductDTO.getCategoryId() == null) {
//            throw new RuntimeException("Category must be provided");
//        }
//        if (avatar != null && !avatar.isEmpty()) {
//            String avatarUrl = cloudinaryService.uploadImage(avatar);
//            existingProduct.setAvatar(avatarUrl);
//        }
//
//        existingProduct.setUpdatedAt(LocalDateTime.now());
//        return productsRepository.save(existingProduct);
//    }
//
//    @Override
//    public void deleteProduct(Integer productId) {
//        if (productsRepository.existsById(productId)) {
//            productsRepository.deleteById(productId);
//        } else {
//            throw new RuntimeException("Product not found with ID: " + productId);
//        }
//    }
//
//    @Override
//    public Products updateProductQuantity(Integer productId, Integer quantity) {
//        Products product = productsRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
//        product.setQuantity(quantity);
//        product.setUpdatedAt(LocalDateTime.now());
//        return productsRepository.save(product);
//    }
//
//    private ProductsDTO convertToDTO(Products product) {
//        ProductsDTO dto = new ProductsDTO();
//        dto.setProductId(product.getProductId());
//        dto.setCategoryId(product.getCategory().getCategoryId());
//        dto.setProductName(product.getProductName());
//        dto.setDescription(product.getDescription());
//        dto.setPrice(product.getPrice());
//        dto.setQuantity(product.getQuantity());
//        dto.setAvatar(product.getAvatar());
//        dto.setCreatedAt(product.getCreatedAt());
//        dto.setUpdatedAt(product.getUpdatedAt());
//        dto.setCategoryName(product.getCategory().getCategoryName());
//        return dto;
//    }
//}


package com.ntq.putanest.serviceimpl;

import com.ntq.putanest.dto.ProductsDTO;
import com.ntq.putanest.pojo.Categories;
import com.ntq.putanest.pojo.Products;
import com.ntq.putanest.repository.CategoriesRepository;
import com.ntq.putanest.repository.ProductsRepository;
import com.ntq.putanest.service.CloudinaryService;
import com.ntq.putanest.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductsService {

    private final ProductsRepository productsRepository;
    private final CloudinaryService cloudinaryService;
    private final CategoriesRepository categoriesRepository;

    @Autowired
    public ProductsServiceImpl(ProductsRepository productsRepository, CloudinaryService cloudinaryService, CategoriesRepository categoriesRepository) {
        this.productsRepository = productsRepository;
        this.cloudinaryService = cloudinaryService;
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    public List<ProductsDTO> getAllProducts() {
        return productsRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductsDTO> getProductById(Integer productId) {
        return productsRepository.findById(productId)
                .map(this::convertToDTO);
    }

    @Override
    public List<ProductsDTO> getProductsByCategoryId(Integer categoryId) {
        return productsRepository.findByCategory_CategoryId(categoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductsDTO createProduct(ProductsDTO productDTO, MultipartFile avatar) throws IOException {
        Products product = convertToEntity(productDTO);

        if (product.getCategory() == null || product.getCategory().getCategoryId() == null) {
            throw new RuntimeException("Category must be provided");
        }

        Categories category = categoriesRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + productDTO.getCategoryId()));
        product.setCategory(category);

        if (avatar != null && !avatar.isEmpty()) {
            String avatarUrl = cloudinaryService.uploadImage(avatar);
            product.setAvatar(avatarUrl);
        }

        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        Products savedProduct = productsRepository.save(product);
        return convertToDTO(savedProduct);
    }

    @Override
    public ProductsDTO updateProduct(Integer productId, ProductsDTO updateProductDTO, MultipartFile avatar) throws IOException {
        Products existingProduct = productsRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        existingProduct.setProductName(updateProductDTO.getProductName());
        existingProduct.setDescription(updateProductDTO.getDescription());
        existingProduct.setPrice(updateProductDTO.getPrice());
        existingProduct.setQuantity(updateProductDTO.getQuantity());

        Categories category = categoriesRepository.findById(updateProductDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + updateProductDTO.getCategoryId()));
        existingProduct.setCategory(category);

        if (avatar != null && !avatar.isEmpty()) {
            String avatarUrl = cloudinaryService.uploadImage(avatar);
            existingProduct.setAvatar(avatarUrl);
        }

        existingProduct.setUpdatedAt(LocalDateTime.now());
        Products updatedProduct = productsRepository.save(existingProduct);
        return convertToDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Integer productId) {
        if (productsRepository.existsById(productId)) {
            productsRepository.deleteById(productId);
        } else {
            throw new RuntimeException("Product not found with ID: " + productId);
        }
    }

    @Override
    public List<Products> searchProductsByProductName(String productName) {
        return productsRepository.findByProductNameContainingIgnoreCase(productName);
    }

    private ProductsDTO convertToDTO(Products product) {
        ProductsDTO dto = new ProductsDTO();
        dto.setProductId(product.getProductId());
        dto.setCategoryId(product.getCategory().getCategoryId());
        dto.setCategoryName(product.getCategory().getCategoryName());
        dto.setProductName(product.getProductName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setAvatar(product.getAvatar());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        return dto;
    }

    private Products convertToEntity(ProductsDTO dto) {
        Products product = new Products();
        product.setProductId(dto.getProductId());
        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setAvatar(dto.getAvatar());

        if (dto.getCategoryId() != null) {
            Categories category = new Categories();
            category.setCategoryId(dto.getCategoryId());
            product.setCategory(category);
        }
        return product;
    }
}