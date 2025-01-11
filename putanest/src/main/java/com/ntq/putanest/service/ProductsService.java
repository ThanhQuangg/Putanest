package com.ntq.putanest.service;
//
//import com.ntq.putanest.dto.ProductsDTO;
//import com.ntq.putanest.pojo.Products;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Optional;
//
//public interface ProductsService {
//    List<ProductsDTO> getAllProducts();
//    Optional<ProductsDTO> getProductById(Integer productId);
//    List<ProductsDTO> getProductsByCategoryId(Integer categoryId);
//    Products createProduct(Products product, MultipartFile avatar) throws IOException;
//    Products updateProduct(Integer productId, ProductsDTO updateProductDTO, MultipartFile avatar) throws IOException;
//    void deleteProduct(Integer productId);
//    Products updateProductQuantity(Integer productId, Integer quantity);
//}

import com.ntq.putanest.dto.ProductsDTO;
import com.ntq.putanest.pojo.Products;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductsService {
    List<ProductsDTO> getAllProducts();
    Optional<ProductsDTO> getProductById(Integer productId);
    List<ProductsDTO> getProductsByCategoryId(Integer categoryId);
    ProductsDTO createProduct(ProductsDTO productDTO, MultipartFile avatar) throws IOException;
    ProductsDTO updateProduct(Integer productId, ProductsDTO updateProductDTO, MultipartFile avatar) throws IOException;
    void deleteProduct(Integer productId);
    List<Products> searchProductsByProductName(String productName);
}

