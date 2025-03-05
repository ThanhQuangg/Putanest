package com.ntq.putanest.controller;

import com.ntq.putanest.dto.ProductsDTO;
import com.ntq.putanest.pojo.Products;
import com.ntq.putanest.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/search-name")
    public ResponseEntity<List<Products>> searchProducts(@RequestParam("q") String query) {
        System.out.println("Search query: " + query);
        List<Products> products = productsService.searchProductsByProductName(query);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<Products>> getPaginatedProducts(
            @RequestParam(defaultValue = "0") int page,  // Trang mặc định là 0
            @RequestParam(defaultValue = "2") int size // Kích thước mặc định là 10
    ) {
        Page<Products> paginatedProducts = productsService.getPaginatedProducts(page, size);
        return ResponseEntity.ok(paginatedProducts);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Products>> searchProducts(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) Integer quantity,
            @RequestParam(required = false) Integer categoryId
    ) {
        List<Products> products = productsService.searchProduct(productName, description, price, quantity,categoryId);
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