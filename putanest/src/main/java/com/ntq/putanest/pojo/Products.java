package com.ntq.putanest.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private Integer productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "CategoryID")
    private Categories category;

    @Column(name = "ProductName", nullable = false, length = 100)
    private String productName;

    @Column(name = "Description")
    private String description;

    @Column(name = "Price", nullable = false)
    private BigDecimal price;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt")
    private LocalDateTime updatedAt;

    public Integer getProductId() {
        return this.productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }


    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }
}
