package com.ntq.putanest.controller;

import com.ntq.putanest.dto.ProductsDTO;
import com.ntq.putanest.pojo.Categories;
import com.ntq.putanest.pojo.Products;
import com.ntq.putanest.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


//@RestController
//@RequestMapping("/api/products")
//public class ProductsController {
//
//    private final ProductsService productsService;
//
//    @Autowired
//    public ProductsController(ProductsService productsService) {
//        this.productsService = productsService;
//    }
//
//    // Lấy tất cả sản phẩm và chuyển sang DTO
//    @GetMapping
//    public List<ProductsDTO> getAllProducts() {
//        return productsService.getAllProducts();
//    }
//
//    // Lấy sản phẩm theo ID và trả về DTO
//    @GetMapping("/{id}")
//    public ResponseEntity<ProductsDTO> getProductById(@PathVariable("id") Integer id) {
//        Optional<ProductsDTO> product = productsService.getProductById(id);
//        return product.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    // Lấy danh sách sản phẩm theo categoryId
//    @GetMapping("/category/{categoryId}")
//    public ResponseEntity<List<ProductsDTO>> getProductsByCategoryId(@PathVariable("categoryId") Integer categoryId) {
//        List<ProductsDTO> products = productsService.getProductsByCategoryId(categoryId);
//
//        if (products.isEmpty()) {
//            return ResponseEntity.noContent().build(); // Trả về 204 nếu không có sản phẩm
//        }
//        return ResponseEntity.ok(products); // Trả về 200 và danh sách sản phẩm
//    }
//
//    // Tạo sản phẩm mới
//        @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//        public ResponseEntity<Products> createProduct(
//                @RequestParam("productName") String productName,
//                @RequestParam("categoryId") Integer categoryId,
//                @RequestParam("description") String description,
//                @RequestParam("price") BigDecimal price,
//                @RequestParam("quantity") Integer quantity,
//                @RequestParam("avatar") MultipartFile avatar) throws IOException {
//
//            if (productName == null || price == null || quantity == null || categoryId == null) {
//                return ResponseEntity.badRequest().body(null);
//            }
//
//            Products product = new Products();
//            product.setProductName(productName);
//            product.setDescription(description);
//            product.setPrice(price);
//            product.setQuantity(quantity);
//
//            Categories category = new Categories();
//            category.setCategoryId(categoryId);
//            product.setCategory(category);
//
//            Products createdProduct = productsService.createProduct(product, avatar);
//            return ResponseEntity.ok(createdProduct);
//        }
//    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<Products> createProduct(@ModelAttribute ProductsDTO productCreateDTO) throws IOException {
//        if (productCreateDTO.getProductName() == null || productCreateDTO.getPrice() == null ||
//                productCreateDTO.getQuantity() == null || productCreateDTO.getCategoryId() == null) {
//            return ResponseEntity.badRequest().body(null);
//        }
//
//        Products product = new Products();
//        product.setProductName(productCreateDTO.getProductName());
//        product.setDescription(productCreateDTO.getDescription());
//        product.setPrice(productCreateDTO.getPrice());
//        product.setQuantity(productCreateDTO.getQuantity());
//
//        Categories category = new Categories();
//        category.setCategoryId(productCreateDTO.getCategoryId());
//        product.setCategory(category);
//
//        Products createdProduct = productsService.createProduct(product, ProductsDTO.getAvatar());
//        return ResponseEntity.ok(createdProduct);
//    }
//
//    // Cập nhật sản phẩm
//    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<Products> updateProduct(
//            @PathVariable("id") Integer id,
//            @RequestParam("productName") String productName,
//            @RequestParam("price") BigDecimal price,
//            @RequestParam("quantity") Integer quantity,
//            @RequestParam("categoryId") Integer categoryId,
//            @RequestParam("description") String description,
//            @RequestParam(value = "avatar", required = false) MultipartFile avatar) throws IOException {
//
//        Optional<ProductsDTO> existingProduct = productsService.getProductById(id);
//        if (existingProduct.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        ProductsDTO updateProductDTO = new ProductsDTO();
//        updateProductDTO.setProductName(productName);
//        updateProductDTO.setPrice(price);
//        updateProductDTO.setQuantity(quantity);
//        updateProductDTO.setCategoryId(categoryId);
//        updateProductDTO.setDescription(description);
//
//        Products updatedProduct = productsService.updateProduct(id, updateProductDTO, avatar);
//        return ResponseEntity.ok(updatedProduct);
//    }
//
//    // Xóa sản phẩm
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Integer id) {
//        productsService.deleteProduct(id);
//        return ResponseEntity.noContent().build();
//    }
//}




@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private final ProductsService productsService;

    @Autowired
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public ResponseEntity<List<ProductsDTO>> getAllProducts() {
        List<ProductsDTO> products = productsService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductsDTO> getProductById(@PathVariable Integer productId) {
        Optional<ProductsDTO> product = productsService.getProductById(productId);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Lấy danh sách sản phẩm theo categoryId
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductsDTO>> getProductsByCategoryId(@PathVariable("categoryId") Integer categoryId) {
        List<ProductsDTO> products = productsService.getProductsByCategoryId(categoryId);

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build(); // Trả về 204 nếu không có sản phẩm
        }
        return ResponseEntity.ok(products); // Trả về 200 và danh sách sản phẩm
    }

    @GetMapping("/search")
    public ResponseEntity<List<Products>> searchProducts(@RequestParam("q") String query) {
        System.out.println("Search query: " + query);
        List<Products> products = productsService.searchProductsByProductName(query);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<ProductsDTO> createProduct(@RequestPart("product") ProductsDTO productsDTO,
                                                     @RequestPart(value = "avatar", required = false) MultipartFile avatar) throws IOException {
        ProductsDTO createdProduct = productsService.createProduct(productsDTO, avatar);
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductsDTO> updateProduct(@PathVariable Integer productId,
                                                     @RequestPart("product") ProductsDTO productsDTO,
                                                     @RequestPart(value = "avatar", required = false) MultipartFile avatar) throws IOException {
        ProductsDTO updatedProduct = productsService.updateProduct(productId, productsDTO, avatar);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId) {
        productsService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}